package ir.rama.taskmanagement.Board.Controller;

import ir.rama.taskmanagement.Board.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Board.Payload.Request.MemberRequest;
import ir.rama.taskmanagement.Board.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Board.Service.BoardService;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @CrossOrigin
    public ResponseEntity<CrudResponse> create(@RequestBody CreationRequest request) {
        return boardService.create(request).getResponse();
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<CrudResponse> read(@RequestParam("board_id") Integer id, CrudRequest request) {
        return boardService.read(request, id).getResponse();
    }

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody UpdateRequest request) {
        return boardService.update(request).getResponse();
    }

    @DeleteMapping
    public ResponseEntity<CrudResponse> delete(@RequestParam("board_id") Integer id, CrudRequest request) {
        return boardService.delete(request, id).getResponse();
    }

    @PostMapping("/member")
    public ResponseEntity<CrudResponse> addMember(@RequestBody MemberRequest request) {
        return boardService.addMember(request).getResponse();
    }

    @DeleteMapping("/member")
    public ResponseEntity<CrudResponse> removeMember(@RequestBody MemberRequest request) {
        return boardService.removeMember(request).getResponse();
    }
}
