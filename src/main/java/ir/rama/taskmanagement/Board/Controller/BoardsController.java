package ir.rama.taskmanagement.Board.Controller;

import ir.rama.taskmanagement.Board.Service.BoardService;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/workspace")
@RequiredArgsConstructor
public class BoardsController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestParam(name = "userId") Integer userId) {
        return boardService.readAll(userId).getResponse();
    }
}
