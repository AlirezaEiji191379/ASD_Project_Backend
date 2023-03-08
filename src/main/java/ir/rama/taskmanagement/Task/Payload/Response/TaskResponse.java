package ir.rama.taskmanagement.Task.Payload.Response;

import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse implements CrudResponse {

    private Integer id;

    private TaskPriority priority;

    private String title;

    private String description;

    private String deadline;

    private Integer columnId;

    private Integer userId;
}
