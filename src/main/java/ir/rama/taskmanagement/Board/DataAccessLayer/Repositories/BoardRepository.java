package ir.rama.taskmanagement.Board.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
