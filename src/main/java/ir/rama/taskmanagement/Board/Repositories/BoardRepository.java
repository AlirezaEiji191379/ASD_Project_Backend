package ir.rama.taskmanagement.Board.Repositories;

import ir.rama.taskmanagement.Board.Entities.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Override
    Optional<BoardEntity> findById(Long id);
}
