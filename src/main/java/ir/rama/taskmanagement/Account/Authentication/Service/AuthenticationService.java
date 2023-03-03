package ir.rama.taskmanagement.Account.Authentication.Service;

import ir.rama.taskmanagement.Account.Authentication.Payload.Response.MessageResponse;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.Response;
import ir.rama.taskmanagement.Account.User.DataAccessLayer.Repositories.UserRepository;
import ir.rama.taskmanagement.Account.Authentication.Payload.AuthenticationRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.AuthenticationResponse;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.Token;
import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.TokenRepository;
import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.Role;
import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Response> signUp(AuthenticationRequest request) {
        var existedUser = userRepository.findByEmail(request.getEmail());
        if (existedUser.isPresent()) {
            return ResponseEntity.badRequest().body(
                    MessageResponse.builder()
                            .message("This email is already exist!!")
                            .build()
            );
        }
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        this.saveUserToken(savedUser, jwtToken);
        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build()
        );
    }

    public ResponseEntity<Response> signIn(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var jwtToken = jwtService.generateToken(user.get());
            this.revokeAllUserTokens(user.get());
            this.saveUserToken(user.get(), jwtToken);
            return ResponseEntity.ok(
                    AuthenticationResponse.builder()
                            .token(jwtToken)
                            .build()
            );
        } else {
            return ResponseEntity.badRequest().body(
                    MessageResponse.builder()
                            .message("This email is not exist!!")
                            .build()
            );
        }
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
        validUserTokens.forEach(token -> {
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
