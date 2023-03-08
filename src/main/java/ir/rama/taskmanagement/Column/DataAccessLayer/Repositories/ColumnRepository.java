package ir.rama.taskmanagement.Column.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Column.DataAccessLayer.Entities.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column, Integer> {

    List<Column> findAllByBoard(Board board);
}
