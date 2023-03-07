package ir.rama.taskmanagement.Task.Payload.Response;

import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse implements CrudResponse {

    private Integer id;

    private TaskPriority priority;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private Integer columnId;

    private List<Integer> users;
}
