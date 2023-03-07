package ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.Token;
import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  List<Token> findAllByUserAndExpired(User user, boolean expired);

  Optional<Token> findByToken(String token);
}
