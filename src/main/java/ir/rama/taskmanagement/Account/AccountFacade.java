package ir.rama.taskmanagement.Account;

import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Account.User.DataAccessLayer.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountFacade {

    private UserRepository userRepository;

    public List<User> findUsers(List<Integer> userIds) {
        return userRepository.findAllById(userIds);
    }
}
