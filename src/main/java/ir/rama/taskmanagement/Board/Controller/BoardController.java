package ir.rama.taskmanagement.Board.Controller;

import ir.rama.taskmanagement.Board.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Board.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Board.Service.BoardService;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CrudResponse> create(@RequestBody CreationRequest request) {
        return boardService.create(request).getResponse();
    }

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestParam Integer id) {
        return boardService.read(id).getResponse();
    }

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody UpdateRequest request) {
        return boardService.update(request).getResponse();
    }

    @DeleteMapping
    public ResponseEntity<CrudResponse> delete(@RequestParam Integer id) {
        return boardService.delete(id).getResponse();
    }
}
