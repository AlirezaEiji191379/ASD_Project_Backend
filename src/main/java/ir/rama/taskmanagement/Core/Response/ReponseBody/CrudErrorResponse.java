package ir.rama.taskmanagement.Core.Response.ReponseBody;

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
