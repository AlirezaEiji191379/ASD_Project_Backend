package ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, Integer> {
}
