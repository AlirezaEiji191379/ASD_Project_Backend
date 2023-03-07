package ir.rama.taskmanagement.Account.User.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  @Cacheable("users")
  Optional<User> findByEmail(String email);
}
