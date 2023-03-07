package ir.rama.taskmanagement.Board.API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/board")
public class BoardController {
    @GetMapping("/test")
    public String test() {
        return "board test";
    }
}
