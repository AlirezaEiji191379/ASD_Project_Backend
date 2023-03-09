package ir.rama.taskmanagement.Core.Response.ResponseStatus;

import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudResponse;
import org.springframework.http.ResponseEntity;

public interface CrudStatusResponse {

    ResponseEntity<CrudResponse> getResponse();
}
