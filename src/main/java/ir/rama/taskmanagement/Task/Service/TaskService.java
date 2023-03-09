package ir.rama.taskmanagement.Task.Service;

import ir.rama.taskmanagement.Account.AccountFacade;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Board.BoardFacade;
import ir.rama.taskmanagement.Column.ColumnFacade;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudSuccessResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.Task;
import ir.rama.taskmanagement.Task.DataAccessLayer.Repositories.TaskRepository;
import ir.rama.taskmanagement.Task.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Task.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Task.Payload.Response.DeleteResponse;
import ir.rama.taskmanagement.Task.Payload.Response.TaskResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AccountFacade accountFacade;
    private final BoardFacade boardFacade;
    private final ColumnFacade columnFacade;

    public CrudStatusResponse create(CreationRequest request) {
        try {
            Assert.hasText(request.getTitle(), "title could not be empty");
            Assert.notNull(request.getPriority(), "priority could not be empty");
            var column = columnFacade.findColumn(request.getColumnId())
                    .orElseThrow(() -> new EntityNotFoundException("Column not found!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), column.getBoard()),
                    "You don't have access to board!!"
            );
            if (request.getUserId() == null) {
                request.setUserId(this.getLoggedUser(request).getId());
            }
            var task = taskRepository.save(
                    Task.builder()
                            .priority(request.getPriority())
                            .title(request.getTitle())
                            .description(request.getDescription())
                            .deadline(
                                    request.getDeadline() != null
                                            ? LocalDateTime.parse(request.getDeadline(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                            : null
                            )
                            .column(column)
                            .user(
                                    accountFacade.findUser(request.getUserId())
                                            .orElseThrow(() -> new EntityNotFoundException("User not found!!"))
                            )
                            .build()
            );
            return CrudSuccessResponse.builder()
                    .response(
                            TaskResponse.builder()
                                    .id(task.getId())
                                    .title(task.getTitle())
                                    .priority(task.getPriority())
                                    .description(task.getDescription())
                                    .deadline(task.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                                    .columnId(task.getColumn().getId())
                                    .userId(task.getUser().getId())
                                    .build()
                    )
                    .build();
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

    public CrudStatusResponse read(CrudRequest request, Integer id) {
        try {
            Assert.notNull(id, "Invalid task id is provided!!");
            var task = taskRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid task id is provided!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), task.getColumn().getBoard()),
                    "You don't have access to board!!"
            );
            return CrudSuccessResponse.builder()
                    .response(
                            TaskResponse.builder()
                                    .id(task.getId())
                                    .title(task.getTitle())
                                    .priority(task.getPriority())
                                    .description(task.getDescription())
                                    .deadline(task.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                                    .columnId(task.getColumn().getId())
                                    .userId(task.getUser().getId())
                                    .build()
                    )
                    .build();
        } catch (EntityNotFoundException | EmptyResultDataAccessException exception) {
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

    public CrudStatusResponse update(UpdateRequest request) {
        try {
            Assert.notNull(request.getId(), "Invalid task id is provided!!");
            var task = taskRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Task not found!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), task.getColumn().getBoard()),
                    "You don't have access to board!!"
            );
            if (request.getPriority() != null) {
                task.setPriority(request.getPriority());
            }
            if (StringUtils.hasText(request.getTitle())) {
                task.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                task.setDescription(request.getDescription());
            }
            if (request.getDeadline() != null) {
                task.setDeadline(request.getDeadline());
            }
            if (request.getColumnId() != null) {
                var newColumn = columnFacade.findColumn(request.getColumnId())
                        .orElseThrow(() -> new EntityNotFoundException("Column not found!!"));
                Assert.isTrue(
                        Objects.equals(newColumn.getBoard().getId(), task.getColumn().getBoard().getId()),
                        "Column is not in the board!!"
                );
                task.setColumn(newColumn);
            }
            if (request.getUserId() != null) {
                task.setUser(
                        accountFacade.findUser(request.getUserId())
                                .orElseThrow(() -> new EntityNotFoundException("User not found!!"))
                );
            }
            task = taskRepository.save(task);
            return new CrudSuccessResponse(
                    TaskResponse.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .priority(task.getPriority())
                            .description(task.getDescription())
                            .deadline(task.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                            .columnId(task.getColumn().getId())
                            .userId(task.getUser().getId())
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
            Assert.notNull(id, "Invalid task id is provided!!");
            var task = taskRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Task not found!!"));
            Assert.isTrue(
                    boardFacade.hasAccessToBoard(this.getLoggedUser(request), task.getColumn().getBoard()),
                    "You don't have access to board!!"
            );
            taskRepository.deleteById(id);
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
