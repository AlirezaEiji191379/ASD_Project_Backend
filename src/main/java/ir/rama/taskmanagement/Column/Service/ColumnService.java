package ir.rama.taskmanagement.Column.Service;

import ir.rama.taskmanagement.Account.AccountFacade;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Board.BoardFacade;
import ir.rama.taskmanagement.Column.DataAccessLayer.Entities.Column;
import ir.rama.taskmanagement.Column.DataAccessLayer.Repositories.ColumnRepository;
import ir.rama.taskmanagement.Column.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Column.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Column.Payload.Response.ColumnResponse;
import ir.rama.taskmanagement.Column.Payload.Response.DeleteResponse;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudSuccessResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.Task;
import ir.rama.taskmanagement.Task.Payload.Response.TaskResponse;
import ir.rama.taskmanagement.Task.TaskFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final TaskFacade taskFacade;
    private final BoardFacade boardFacade;
    private final AccountFacade accountFacade;
    private final ColumnRepository columnRepository;

    public CrudStatusResponse create(CreationRequest request) {
        try {
            Assert.hasText(request.getTitle(), "title could not be empty");
            Assert.notNull(request.getBoardId(), "board id could not be empty");
            var board = boardFacade.findBoard(request.getBoardId())
                    .orElseThrow(() -> new EntityNotFoundException("Board not found!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), board),
                    "You don't have access to board!!"
            );
            var column = columnRepository.save(
                    Column.builder()
                            .title(request.getTitle())
                            .board(board)
                            .build()
            );
            return CrudSuccessResponse.builder()
                    .response(
                            ColumnResponse.builder()
                                    .id(column.getId())
                                    .title(column.getTitle())
                                    .build()
                    )
                    .build();
        } catch (EntityNotFoundException | IllegalArgumentException exception) {
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
            Assert.notNull(id, "Invalid column id is provided!!");
            var column = columnRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid column id is provided!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), column.getBoard()),
                    "You don't have access to board!!"
            );
            return CrudSuccessResponse.builder()
                    .response(
                            ColumnResponse.builder()
                                    .id(column.getId())
                                    .title(column.getTitle())
                                    .tasks(
                                            column.getTasks()
                                                    .stream()
                                                    .sorted(Comparator.comparingInt(t -> t.getPriority().getValue()))
                                                    .map(
                                                            task -> TaskResponse.builder()
                                                                    .id(task.getId())
                                                                    .title(task.getTitle())
                                                                    .priority(task.getPriority())
                                                                    .description(task.getDescription())
                                                                    .deadline(task.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                                                                    .columnId(task.getColumn().getId())
                                                                    .build()
                                                    )
                                                    .toList()
                                    )
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
            Assert.notNull(request.getId(), "Invalid column id is provided!!");
            var column = columnRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Invalid column id is provided!!"));
            if (StringUtils.hasText(request.getTitle())) {
                column.setTitle(request.getTitle());
                column = columnRepository.save(column);
            }
            return new CrudSuccessResponse(
                    ColumnResponse.builder()
                            .id(column.getId())
                            .title(column.getTitle())
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
            Assert.notNull(id, "Invalid column id is provided!!");
            var column = columnRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid board id is provided!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), column.getBoard()),
                    "You don't have access to board!!"
            );
            var taskIds = column.getTasks().stream().map(Task::getId).toList();
            taskFacade.deleteAllTasks(taskIds);
            columnRepository.deleteById(id);
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

    private User getLoggedUser(CrudRequest request) throws EntityNotFoundException {
        return accountFacade.findLoggedUser(request.getUsername());
    }
}
