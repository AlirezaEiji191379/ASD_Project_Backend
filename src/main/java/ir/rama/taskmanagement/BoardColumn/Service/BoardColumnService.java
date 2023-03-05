package ir.rama.taskmanagement.BoardColumn.Service;

import ir.rama.taskmanagement.Board.BoardFacade;
import ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Entities.BoardColumn;
import ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Repositories.BoardColumnRepository;
import ir.rama.taskmanagement.BoardColumn.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.BoardColumn.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.BoardColumn.Payload.Response.BoardColumnResponse;
import ir.rama.taskmanagement.BoardColumn.Payload.Response.DeleteResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudSuccessResponse;
import ir.rama.taskmanagement.Task.Payload.Response.TaskResponse;
import ir.rama.taskmanagement.Task.TaskFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private TaskFacade taskFacade;
    private BoardFacade boardFacade;

    private BoardColumnRepository boardColumnRepository;

    public CrudStatusResponse create(CreationRequest request) {
        try {
            Assert.hasText(request.getTitle(), "title could not be empty");
            Assert.notNull(request.getBoardId(), "board id could not be empty");
            var board = boardFacade.findBoard(request.getBoardId())
                    .orElseThrow(() -> new EntityNotFoundException("Board not found!!"));
            var column = boardColumnRepository.save(
                    BoardColumn.builder()
                            .title(request.getTitle())
                            .board(board)
                            .build()
            );
            return CrudSuccessResponse.builder()
                    .response(
                            BoardColumnResponse.builder()
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

    public CrudStatusResponse read(Integer id) {
        try {
            Assert.notNull(id, "Invalid column id is provided!!");
            var column = boardColumnRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid column id is provided!!"));
            return CrudSuccessResponse.builder()
                    .response(
                            BoardColumnResponse.builder()
                                    .id(column.getId())
                                    .title(column.getTitle())
                                    .tasks(
                                            column.getTasks()
                                                    .stream()
                                                    .map(
                                                            task -> TaskResponse.builder()
                                                                    .id(task.getId())
                                                                    .title(task.getTitle())
                                                                    .priority(task.getPriority())
                                                                    .description(task.getDescription())
                                                                    .deadline(task.getDeadline())
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
            var column = boardColumnRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Invalid column id is provided!!"));
            if (StringUtils.hasText(request.getTitle())) {
                column.setTitle(request.getTitle());
                column = boardColumnRepository.save(column);
            }
            return new CrudSuccessResponse(
                    BoardColumnResponse.builder()
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

    public CrudStatusResponse delete(Integer id) {
        try {
            Assert.notNull(id, "Invalid column id is provided!!");
            var column = boardColumnRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invalid board id is provided!!"));
            var tasks = column.getTasks();
            tasks.forEach(task -> taskFacade.deleteTask(task));
            boardColumnRepository.deleteById(id);
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
}
