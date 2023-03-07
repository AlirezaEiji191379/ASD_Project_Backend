package ir.rama.taskmanagement.Board.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBoardRepository extends JpaRepository<UserBoard, Integer> {

    List<UserBoard> findAllByUserId(Integer userId);
}
