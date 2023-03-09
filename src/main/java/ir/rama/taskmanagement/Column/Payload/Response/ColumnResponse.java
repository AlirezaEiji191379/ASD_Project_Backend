package ir.rama.taskmanagement.Column.Payload.Response;

import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudResponse;
import ir.rama.taskmanagement.Task.Payload.Response.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnResponse implements CrudResponse {

    private Integer id;

    private String title;

    private List<TaskResponse> tasks;
}
