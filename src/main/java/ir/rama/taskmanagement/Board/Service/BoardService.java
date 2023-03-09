package ir.rama.taskmanagement.Board.Service;

import ir.rama.taskmanagement.Account.AccountFacade;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.UserBoard;
import ir.rama.taskmanagement.Board.DataAccessLayer.Repositories.BoardRepository;
import ir.rama.taskmanagement.Board.DataAccessLayer.Repositories.UserBoardRepository;
import ir.rama.taskmanagement.Board.Payload.Request.MemberRequest;
import ir.rama.taskmanagement.Board.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Board.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Board.Payload.Response.BoardResponse;
import ir.rama.taskmanagement.Board.Payload.Response.DeleteResponse;
import ir.rama.taskmanagement.Board.Payload.Response.MemberResponse;
import ir.rama.taskmanagement.Board.Payload.Response.WorkspaceResponse;
import ir.rama.taskmanagement.Column.ColumnFacade;
import ir.rama.taskmanagement.Column.DataAccessLayer.Entities.Column;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudSuccessResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.Task;
import ir.rama.taskmanagement.Task.TaskFacade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final AccountFacade accountFacade;
    private final ColumnFacade columnFacade;
    private final TaskFacade taskFacade;
    private final UserBoardRepository userBoardRepository;

    public CrudStatusResponse create(CreationRequest request) {
        try {
            Assert.hasText(request.getTitle(), "title could not be empty");
            var board = this.createBoard(request);
            return CrudSuccessResponse.builder()
                    .response(
                            BoardResponse.builder()
                                    .id(board.getId())
                                    .title(board.getTitle())
                                    .build()
                    )
                    .build();
        } catch (IllegalArgumentException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse read(CrudRequest request, Integer id) {
        try {
            Assert.notNull(id, "Invalid board id is provided!!");
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid board id is provided!!"));
            Assert.isTrue(
                    hasAccessToBoard(this.getLoggedUser(request), board),
                    "You don't have access to board"
            );
            return CrudSuccessResponse.builder()
                    .response(
                            BoardResponse.builder()
                                    .id(board.getId())
                                    .title(board.getTitle())
                                    .build()
                    )
                    .build();
        } catch (EntityNotFoundException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse update(UpdateRequest request) {
        try {
            Assert.notNull(request.getId(), "Invalid board id is provided!!");
            Board board = boardRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Invalid board id is provided!!"));
            if (StringUtils.hasText(request.getTitle())) {
                board.setTitle(request.getTitle());
                board = boardRepository.save(board);
            }
            return new CrudSuccessResponse(
                    BoardResponse.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .build()
            );
        } catch (IllegalArgumentException | EntityNotFoundException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse delete(CrudRequest request, Integer id) {
        try {
            Assert.notNull(id, "Invalid board id is provided!!");
            this.deleteBoard(request, id);
            return CrudSuccessResponse.builder()
                    .response(
                            DeleteResponse.builder()
                                    .id(id)
                                    .build()
                    )
                    .build();
        } catch (EmptyResultDataAccessException | IllegalArgumentException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse readAll(CrudRequest request) {
        try {
            var user = this.getLoggedUser(request);
            var boards = userBoardRepository.findAllByUserId(user.getId());
            return CrudSuccessResponse.builder()
                    .response(
                            WorkspaceResponse.builder()
                                    .boards(
                                            boards.stream()
                                                    .map(
                                                            userBoard -> BoardResponse.builder()
                                                                    .id(userBoard.getBoard().getId())
                                                                    .title(userBoard.getBoard().getTitle())
                                                                    .build()
                                                    )
                                                    .toList()
                                    )
                                    .build()
                    )
                    .build();
        } catch (EmptyResultDataAccessException | EntityNotFoundException | IllegalArgumentException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse addMember(MemberRequest request) {
        try {
            Assert.notNull(request.getBoardId(), "Invalid Board id is provided");
            var board = boardRepository.findById(request.getBoardId())
                            .orElseThrow(() -> new EntityNotFoundException("Board not found!!"));
            Assert.isTrue(
                    this.hasAccessToBoard(this.getLoggedUser(request), board),
                    "You don't have access to board!!"
            );
            var invitedUser = accountFacade.findUserOfEmail(request.getEmail());
            this.addUserToBoard(board, invitedUser);
            return CrudSuccessResponse.builder()
                    .response(
                            MemberResponse.builder()
                                    .status(true)
                                    .build()
                    )
                    .build();
        } catch (EmptyResultDataAccessException | EntityNotFoundException | IllegalArgumentException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse removeMember(MemberRequest request) {
        try {
            Assert.notNull(request.getBoardId(), "Invalid Board id is provided");
            var board = boardRepository.findById(request.getBoardId())
                    .orElseThrow(() -> new EntityNotFoundException("Board not found!!"));
            Assert.isTrue(
                    this.hasAccessToBoard(this.getLoggedUser(request), board),
                    "You don't have access to board!!"
            );
            var userToRemove = accountFacade.findUserOfEmail(request.getEmail());
            this.removeUserFromBoard(board, userToRemove);
            return CrudSuccessResponse.builder()
                    .response(
                            MemberResponse.builder()
                                    .status(true)
                                    .build()
                    )
                    .build();
        } catch (EmptyResultDataAccessException | EntityNotFoundException | IllegalArgumentException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    private void removeUserFromBoard(Board board, User user) {
        var userBoard = userBoardRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new EntityNotFoundException("This user is not a member of this board"));
        userBoardRepository.delete(userBoard);
    }


    public boolean hasAccessToBoard(User user, Board board) {
        return userBoardRepository.findByUserAndBoard(user, board).isPresent();
    }

    @Transactional
    private void deleteBoard(CrudRequest request, Integer id) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board does not exist!"));
        Assert.isTrue(userBoardRepository.findByUserAndBoard(
                this.getLoggedUser(request), board).isPresent(), "You don't have access to board"
        );
        var columns = columnFacade.findAllColumns(board);
        var taskIds = columns.stream()
                .map(Column::getTasks)
                .flatMap(Collection::stream)
                .map(Task::getId)
                .toList();
        taskFacade.deleteAllTasks(taskIds);
        columnFacade.deleteAllColumns(columns.stream().map(Column::getId).toList());
        var userBoards = userBoardRepository.findAllByBoardId(id);
        userBoardRepository.deleteAllById(
                userBoards.stream()
                        .map(UserBoard::getId)
                        .toList()
        );
        boardRepository.delete(board);
    }

    @Transactional
    private Board createBoard(CreationRequest request) {
        Board board = boardRepository.save(
                Board.builder()
                        .title(request.getTitle())
                        .build()
        );
        this.addUserToBoard(board, this.getLoggedUser(request));
        return board;
    }

    private void addUserToBoard(Board board, User user) {
        userBoardRepository.save(
                UserBoard.builder()
                        .board(board)
                        .user(user)
                        .build()
        );
    }

    private User getLoggedUser(CrudRequest request) throws EntityNotFoundException {
        return accountFacade.findLoggedUser(request.getUsername());
    }
}
