package ir.rama.taskmanagement.Board.Service;

import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Board.DataAccessLayer.Repositories.UserBoardRepository;
import ir.rama.taskmanagement.Board.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Board.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Board.Payload.Response.BoardResponse;
import ir.rama.taskmanagement.Board.Payload.Response.DeleteResponse;
import ir.rama.taskmanagement.Board.DataAccessLayer.Repositories.BoardRepository;
import ir.rama.taskmanagement.Board.Payload.Response.WorkspaceResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudSuccessResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;

    private UserBoardRepository userBoardRepository;

    public CrudStatusResponse create(CreationRequest request) {
        try {
            Assert.hasText(request.getTitle(), "title could not be empty");
            Board board = boardRepository.save(
                    Board.builder()
                            .title(request.getTitle())
                            .build()
            );
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

    public CrudStatusResponse read(Integer id) {
        try {
            Assert.notNull(id, "Invalid board id is provided!!");
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid board id is provided!!"));
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

    public CrudStatusResponse delete(Integer id) {
        try {
            Assert.notNull(id, "Invalid board id is provided!!");
            boardRepository.deleteById(id);
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

    public CrudStatusResponse readAll(Integer userId) {
        try {
            Assert.notNull(userId, "Invalid user id is provided!!");
            var boards = userBoardRepository.findAllByUserId(userId);
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
}
