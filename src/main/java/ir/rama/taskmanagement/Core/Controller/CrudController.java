package ir.rama.taskmanagement.Core.Controller;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import ir.rama.taskmanagement.Core.Service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class CrudController {

    @PostMapping
    public ResponseEntity<CrudResponse> create(@RequestBody CrudRequest request) {
        return getCrudService().create(request).getResponse();
    }

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestBody CrudRequest request) {
        return getCrudService().read(request).getResponse();
    }

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody CrudRequest request) {
        return getCrudService().update(request).getResponse();
    }

    @DeleteMapping
    public ResponseEntity<CrudResponse> delete(@RequestBody CrudRequest request) {
        return getCrudService().delete(request).getResponse();
    }

    protected abstract CrudService getCrudService();
}
