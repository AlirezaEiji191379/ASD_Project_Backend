package ir.rama.taskmanagement.Account;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountFacade {

    private final UserRepository userRepository;

    public Optional<User> findUser(Integer userId) {
        return userRepository.findById(userId);
    }

    public User findLoggedUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("You are not logged in"));
    }

    public User findUserOfEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is no account with provided email!!"));
    }
}
