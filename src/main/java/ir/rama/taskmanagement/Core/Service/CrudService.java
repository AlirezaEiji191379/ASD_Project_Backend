package ir.rama.taskmanagement.Core.Service;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudStatusResponse;
import org.springframework.stereotype.Service;

@Service
public interface CrudService {

    CrudStatusResponse create(CrudRequest request);

    CrudStatusResponse read(CrudRequest request);

    CrudStatusResponse update(CrudRequest request);

    CrudStatusResponse delete(CrudRequest request);
}
