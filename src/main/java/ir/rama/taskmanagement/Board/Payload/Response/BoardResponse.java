package ir.rama.taskmanagement.Board.Payload.Response;

import ir.rama.taskmanagement.BoardColumn.Payload.Response.BoardColumnResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
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

    private List<BoardColumnResponse> columns;
}
