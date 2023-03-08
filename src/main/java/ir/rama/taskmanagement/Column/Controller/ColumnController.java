package ir.rama.taskmanagement.Column.Controller;

import ir.rama.taskmanagement.Column.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Column.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Column.Service.ColumnService;
import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/column")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<CrudResponse> create(@RequestBody CreationRequest request) {
        return columnService.create(request).getResponse();
    }

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestParam("column_id") Integer id, CrudRequest request) {
        return columnService.read(request, id).getResponse();
    }

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody UpdateRequest request) {
        return columnService.update(request).getResponse();
    }

    @DeleteMapping
    public ResponseEntity<CrudResponse> delete(@RequestParam("column_id") Integer id, CrudRequest request) {
        return columnService.delete(request, id).getResponse();
    }
}
