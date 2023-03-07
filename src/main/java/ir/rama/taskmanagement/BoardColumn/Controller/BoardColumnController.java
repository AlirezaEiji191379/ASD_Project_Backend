package ir.rama.taskmanagement.BoardColumn.Controller;

import ir.rama.taskmanagement.BoardColumn.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.BoardColumn.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.BoardColumn.Service.BoardColumnService;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/column")
@RequiredArgsConstructor
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    @PostMapping
    public ResponseEntity<CrudResponse> create(@RequestBody CreationRequest request) {
        return boardColumnService.create(request).getResponse();
    }

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestParam Integer id) {
        return boardColumnService.read(id).getResponse();
    }

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody UpdateRequest request) {
        return boardColumnService.update(request).getResponse();
    }

    @DeleteMapping
    public ResponseEntity<CrudResponse> delete(@RequestParam Integer id) {
        return boardColumnService.delete(id).getResponse();
    }
}
