package ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
