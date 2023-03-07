package ir.rama.taskmanagement.Account;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountFacade {

    private UserRepository userRepository;

    public Optional<User> findUser(Integer userId) {
        return userRepository.findById(userId);
    }
}
