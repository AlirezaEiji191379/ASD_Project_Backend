package ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  @Cacheable("user:emails")
  Optional<User> findByEmail(String email);


  @Cacheable("user:usernames")
  Optional<User> findByUsername(String username);
}
