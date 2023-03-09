package ir.rama.taskmanagement;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.EmailCheckRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.SignInRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.SignUpRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.TokenValidationRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.AuthenticationResponse;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.EmailCheckResponse;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.TokenValidationResponse;
import ir.rama.taskmanagement.Account.Authentication.Service.AuthenticationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AuthenticationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void signUpTest() {
        var email = "alirezababazadeh11@rama.ir";
        var password = "123456789";
        var response = (AuthenticationResponse)authenticationService.signUp(
                SignUpRequest.builder()
                        .email(email)
                        .password(password)
                        .build()
        ).getResponse().getBody();
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getToken());
    }

    @Test
    public void signInTest() {
        var email = "alirezababazadeh12@rama.ir";
        var password = "123456789";
        userRepository.save(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
        var response = (AuthenticationResponse)authenticationService.signIn(
                SignInRequest.builder()
                        .email(email)
                        .password(password)
                        .build()
        ).getResponse().getBody();
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getToken());
    }

    @Test
    public void validateTokenTest() {
        var email = "alirezababazadeh13@rama.ir";
        var password = "123456789";
        userRepository.save(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
        var response = (AuthenticationResponse)authenticationService.signIn(
                SignInRequest.builder()
                        .email(email)
                        .password(password)
                        .build()
        ).getResponse().getBody();
        Assert.assertNotNull(response);
        var token = response.getToken();
        var mainResponse = (TokenValidationResponse)authenticationService.validateToken(
                TokenValidationRequest.builder()
                        .token(token)
                        .build()
        ).getResponse().getBody();
        Assert.assertNotNull(mainResponse);
        Assert.assertTrue(mainResponse.isStatus());
    }

    @Test
    public void emailCheckTest() {
        var email = "alirezababazadeh14@rama.ir";
        var password = "123456789";
        userRepository.save(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
        var response = (EmailCheckResponse)authenticationService.checkEmail(
                EmailCheckRequest.builder()
                        .email(email)
                        .build()
        ).getResponse().getBody();
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isExist());
    }
}
