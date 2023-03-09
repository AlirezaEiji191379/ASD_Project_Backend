package ir.rama.taskmanagement.Board.Payload.Response;

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
public class WorkspaceResponse implements CrudResponse {

    private List<BoardResponse> boards;
}
