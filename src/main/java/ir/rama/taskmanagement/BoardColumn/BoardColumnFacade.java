package ir.rama.taskmanagement.BoardColumn;

import ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Entities.BoardColumn;
import ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Repositories.BoardColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardColumnFacade {

    private BoardColumnRepository columnRepository;

    public Optional<BoardColumn> findBoardColumn(Integer id) {
        return columnRepository.findById(id);
    }
}
