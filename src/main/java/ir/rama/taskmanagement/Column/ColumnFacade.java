package ir.rama.taskmanagement.Column;

import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Column.DataAccessLayer.Entities.Column;
import ir.rama.taskmanagement.Column.DataAccessLayer.Repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ColumnFacade {

    private final ColumnRepository columnRepository;

    public Optional<Column> findColumn(Integer id) {
        return columnRepository.findById(id);
    }

    public List<Column> findAllColumns(Board board) {
        return columnRepository.findAllByBoard(board);
    }

    public void deleteAllColumns(List<Integer> columnIds) {
        columnRepository.deleteAllById(columnIds);
    }
}
