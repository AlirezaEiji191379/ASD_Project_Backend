package ir.rama.taskmanagement.Board.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.Board;
import ir.rama.taskmanagement.Board.DataAccessLayer.Entities.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBoardRepository extends JpaRepository<UserBoard, Integer> {

    List<UserBoard> findAllByUserId(Integer userId);

    List<UserBoard> findAllByBoardId(Integer boardId);

    Optional<UserBoard> findByUserAndBoard(User user, Board board);
}
