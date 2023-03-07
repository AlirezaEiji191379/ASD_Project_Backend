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
public class CrudSuccessResponse implements CrudStatusResponse {

    private CrudResponse response;

    @Override
    public ResponseEntity<CrudResponse> getResponse() {
        return ResponseEntity.ok().body(response);
    }
}
