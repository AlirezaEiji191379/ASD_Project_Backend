package ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus;

import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import org.springframework.http.ResponseEntity;

public interface CrudStatusResponse {

    ResponseEntity<CrudResponse> getResponse();
}
