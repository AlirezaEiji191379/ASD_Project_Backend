package ir.rama.taskmanagement.Board.Payload.Response;

import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse implements CrudResponse {

    private boolean status;
}
