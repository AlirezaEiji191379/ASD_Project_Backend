package ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus;

import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrudClientErrorResponse implements CrudStatusResponse {

    private CrudResponse error;

    @Override
    public ResponseEntity<CrudResponse> getResponse() {
        return ResponseEntity.badRequest().body(error);
    }
}
