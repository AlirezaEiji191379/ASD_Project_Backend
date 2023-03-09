package ir.rama.taskmanagement.Board.Payload.Response;

import ir.rama.taskmanagement.Column.Payload.Response.ColumnResponse;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse implements CrudResponse {

    private Integer id;

    private String title;

    private List<ColumnResponse> columns;
}
