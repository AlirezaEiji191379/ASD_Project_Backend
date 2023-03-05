package ir.rama.taskmanagement.Core.Payload.Response.ReponseBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrudErrorResponse implements CrudResponse {

    private String message;
}
