package ir.rama.taskmanagement.Board;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Board.DataAccessLayer.Repositories.BoardRepository;
import ir.rama.taskmanagement.Board.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardFacade {

    private final BoardRepository boardRepository;

    private final BoardService boardService;

    public Optional<Board> findBoard(Integer id) {
        return boardRepository.findById(id);
    }

    public boolean hasAccessToBoard(User user, Board board) {
        return boardService.hasAccessToBoard(user, board);
    }
}
