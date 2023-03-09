package ir.rama.taskmanagement;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import ir.rama.taskmanagement.Account.Profile.Service.ProfileService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ProfileTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void readTest() {
    }

    @Test
    public void updateTest() {
    }
}
