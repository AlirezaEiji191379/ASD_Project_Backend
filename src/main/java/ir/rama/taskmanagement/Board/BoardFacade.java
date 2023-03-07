package ir.rama.taskmanagement.Board;

import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Board.DataAccessLayer.Repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardFacade {

    private BoardRepository boardRepository;

    public Optional<Board> findBoard(Integer id) {
        return boardRepository.findById(id);
    }
}
