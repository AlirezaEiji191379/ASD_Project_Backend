package ir.rama.taskmanagement.Account.Profile.Service;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import ir.rama.taskmanagement.Account.Authentication.Service.AuthenticationService;
import ir.rama.taskmanagement.Account.Profile.Payload.Request.ProfileRequest;
import ir.rama.taskmanagement.Account.Profile.Payload.Response.ProfileResponse;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Response.ResponseStatus.CrudSuccessResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationService authenticationService;

    public CrudStatusResponse read(CrudRequest request) {
        try {
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("You are not logged in"));
            return CrudSuccessResponse.builder()
                    .response(
                            ProfileResponse.builder()
                                    .displayName(user.getDisplayName())
                                    .username(user.getUsername())
                                    .email(user.getEmail())
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

    public CrudStatusResponse update(ProfileRequest request) {
        try {
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found!!"));
            if (StringUtils.hasText(request.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            boolean shouldCreateNewToken = false;
            if (StringUtils.hasText(request.getNewUsername())) {
                if (!Objects.equals(user.getUsername(), request.getNewUsername())) {
                    Assert.isTrue(
                            userRepository.findByUsername(request.getNewUsername()).isEmpty(),
                            "username is already exist"
                    );
                    Assert.isTrue(
                            request.getNewUsername()
                                    .matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$"),
                            "Invalid username is provided!!"
                    );
                    user.setUsername(request.getNewUsername());
                    shouldCreateNewToken = true;
                }
            }
            if (StringUtils.hasText(request.getDisplayName())) {
                user.setDisplayName(request.getDisplayName());
            }
            user = userRepository.save(user);
            return CrudSuccessResponse.builder()
                    .response(
                            ProfileResponse.builder()
                                    .displayName(user.getDisplayName())
                                    .username(user.getUsername())
                                    .email(user.getEmail())
                                    .token(
                                            shouldCreateNewToken
                                                    ? authenticationService.updateUserToken(user)
                                                    : null
                                    )
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
}
