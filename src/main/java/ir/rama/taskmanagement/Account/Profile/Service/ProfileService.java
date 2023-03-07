package ir.rama.taskmanagement.Account.Profile.Service;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Repositories.UserRepository;
import ir.rama.taskmanagement.Account.Profile.Payload.Request.ProfileRequest;
import ir.rama.taskmanagement.Account.Profile.Payload.Response.UserResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudClientErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudServerErrorResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudStatusResponse;
import ir.rama.taskmanagement.Core.Payload.Response.ResponseStatus.CrudSuccessResponse;
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

    public CrudStatusResponse read(Integer userId) {
        try {
            Assert.notNull(userId, "User id is null!!");
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found!!"));
            return CrudSuccessResponse.builder()
                    .response(
                            UserResponse.builder()
                                    .id(user.getId())
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
            Assert.notNull(request.getId(), "User id is null!!");
            var user = userRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found!!"));
            if (StringUtils.hasText(request.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if (StringUtils.hasText(request.getUsername())) {
                if (!Objects.equals(user.getUsername(), request.getUsername())) {
                    Assert.isTrue(userRepository.findByUsername(
                            request.getUsername()).isEmpty(),
                            "username is already exist"
                    );
                    user.setUsername(request.getUsername());
                }
            }
            if (StringUtils.hasText(request.getDisplayName())) {
                user.setDisplayName(request.getDisplayName());
            }
            user = userRepository.save(user);
            return CrudSuccessResponse.builder()
                    .response(
                            UserResponse.builder()
                                    .id(user.getId())
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
}
