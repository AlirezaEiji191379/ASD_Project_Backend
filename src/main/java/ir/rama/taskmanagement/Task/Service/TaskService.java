package ir.rama.taskmanagement.Task.Service;

import ir.rama.taskmanagement.Account.AccountFacade;
import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.BoardColumn.BoardColumnFacade;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudSuccessResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.Task;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.UserTask;
import ir.rama.taskmanagement.Task.DataAccessLayer.Repositories.UserTaskRepository;
import ir.rama.taskmanagement.Task.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Task.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Task.Payload.Response.DeleteResponse;
import ir.rama.taskmanagement.Task.Payload.Response.TaskResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    private UserTaskRepository userTaskRepository;

    private AccountFacade accountFacade;

    private BoardColumnFacade boardColumnFacade;

    public CrudStatusResponse create(CreationRequest request) {
        try {
            Assert.hasText(request.getTitle(), "title could not be empty");
            Assert.notNull(request.getPriority(), "priority could not be empty");
            var task = createTaskInTransaction(request);
            return CrudSuccessResponse.builder()
                    .response(
                            TaskResponse.builder()
                                    .id(task.getId())
                                    .title(task.getTitle())
                                    .priority(task.getPriority())
                                    .description(task.getDescription())
                                    .deadline(task.getDeadline())
                                    .columnId(task.getColumn().getId())
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
        }
    }

    public CrudStatusResponse read(Integer id) {
        try {
            Assert.notNull(id, "Invalid task id is provided!!");
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid task id is provided!!"));
            return CrudSuccessResponse.builder()
                    .response(
                            TaskResponse.builder()
                                    .id(task.getId())
                                    .title(task.getTitle())
                                    .priority(task.getPriority())
                                    .description(task.getDescription())
                                    .deadline(task.getDeadline())
                                    .columnId(task.getColumn().getId())
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
            Assert.notNull(request.getId(), "Invalid task id is provided!!");
            Task task = taskRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Invalid task id is provided!!"));
            if (request.getPriority() != null) {
                task.setPriority(request.getPriority());
            }
            if (StringUtils.hasText(request.getTitle())) {
                task.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                task.setTitle(request.getDescription());
            }
            if (request.getDeadline() != null) {
                task.setDeadline(request.getDeadline());
            }
            if (request.getColumnId() != null) {
                var newColumn = boardColumnFacade.findBoardColumn(request.getColumnId())
                        .orElseThrow(() -> new EntityNotFoundException("Column not found!"));
                Assert.isTrue(
                        Objects.equals(newColumn.getBoard().getId(), task.getColumn().getId()),
                        "Column is not in the board!!"
                );
                task.setColumn(newColumn);
            }
            task = taskRepository.save(task);
            return new CrudSuccessResponse(
                    TaskResponse.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .priority(task.getPriority())
                            .description(task.getDescription())
                            .deadline(task.getDeadline())
                            .columnId(task.getColumn().getId())
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
            Assert.notNull(id, "Invalid task id is provided!!");
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

    @Transactional
    private Task createTaskInTransaction(CreationRequest request) {
        Task task = taskRepository.save(
                Task.builder()
                        .priority(request.getPriority())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .deadline(request.getDeadline())
                        .column(
                                boardColumnFacade.findBoardColumn(request.getColumnId())
                                        .orElseThrow(() -> new EntityNotFoundException("Column not found!"))
                        )
                        .build()
        );
        if (!CollectionUtils.isEmpty(request.getUserIds())) {
            var users = accountFacade.findUsers(request.getUserIds())
                    .stream().collect(Collectors.toMap(User::getId, user -> user));
            userTaskRepository.saveAll(
                    request.getUserIds()
                            .stream()
                            .map(
                                    userId -> UserTask.builder()
                                            .user(users.get(userId))
                                            .task(task)
                                            .build()
                            )
                            .toList()
            );
        }
        return task;
    }
    //todo: update in transaction with updating users_tasks table
    //todo: return users data in task response.
}
