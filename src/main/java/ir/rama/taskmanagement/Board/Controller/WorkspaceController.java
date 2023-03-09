package ir.rama.taskmanagement.Board.Controller;

import ir.rama.taskmanagement.Board.Service.BoardService;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final BoardService boardService;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<CrudResponse> read(CrudRequest request) {
        return boardService.readAll(request).getResponse();
    }
}
