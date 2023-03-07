package ir.rama.taskmanagement.Account.Authentication.Service;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.Token;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.TokenRepository;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.EmailCheckRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.TokenValidationRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.SignInRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.SignUpRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.AuthenticationResponse;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.EmailCheckResponse;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.TokenValidationResponse;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.Role;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudSuccessResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public CrudStatusResponse signUp(SignUpRequest request) {
        try {
            Assert.notNull(request.getEmail(), "Email should not be empty");
            userRepository.findByEmail(request.getEmail()).ifPresent(
                    user -> {
                        throw new EntityExistsException("This username is already exist!!");
                    }
            );

            var token = this.createTokenAndSaveUser(request);
            return CrudSuccessResponse.builder()
                    .response(
                            AuthenticationResponse.builder()
                                    .token(token)
                                    .build()
                    )
                    .build();
        } catch (EntityExistsException | IllegalArgumentException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse signIn(SignInRequest request) {
        try {
            Assert.notNull(request.getEmail(), "Email should not be empty");
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Email not found!!"));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var token = this.updateUserToken(user);
            return CrudSuccessResponse.builder()
                    .response(
                            AuthenticationResponse.builder()
                                    .token(token)
                                    .build()
                    )
                    .build();
        } catch (IllegalArgumentException | EntityNotFoundException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse validateToken(TokenValidationRequest request) {
        try {
            Assert.notNull(request.getToken(), "Token is null!!");
            var username = jwtService.extractUsername(request.getToken());
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Token is invalid!!"));
            Assert.isTrue(jwtService.isTokenValid(request.getToken(), user), "Token is invalid!!");
            return CrudSuccessResponse.builder()
                    .response(
                            TokenValidationResponse.builder()
                                    .userId(user.getId())
                                    .build()
                    )
                    .build();
        } catch (IllegalArgumentException | EntityNotFoundException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    public CrudStatusResponse checkEmail(EmailCheckRequest request) {
        try {
            Assert.notNull(request.getEmail(), "Email is null!!");
            var user = userRepository.findByEmail(request.getEmail());
            return CrudSuccessResponse.builder()
                    .response(
                            EmailCheckResponse.builder()
                                    .isExist(user.isPresent())
                                    .build()
                    )
                    .build();
        } catch (IllegalArgumentException | EntityNotFoundException exception) {
            return CrudClientErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        } catch (Exception exception) {
            return CrudServerErrorResponse.builder()
                    .error(
                            CrudErrorResponse.builder()
                                    .message(exception.getMessage())
                                    .build()
                    )
                    .build();
        }
    }

    @Transactional
    private String createTokenAndSaveUser(SignUpRequest request) {
        var user = userRepository.save(
                User.builder()
                        .username(this.findUsername(request))
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build()
        );
        var token = jwtService.generateToken(user);
        this.saveUserToken(user, token);
        return token;
    }

    @Transactional
    private String updateUserToken(User user) {
        var token = jwtService.generateToken(user);
        this.revokeAllUserTokens(user);
        this.saveUserToken(user, token);
        return token;
    }


    private String findUsername(SignUpRequest request) {
        return request.getEmail().substring(0, request.getEmail().indexOf('@')) + new Random().nextInt();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUserAndExpired(user, false);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> token.setExpired(true));
        tokenRepository.saveAll(validUserTokens);
    }
}
