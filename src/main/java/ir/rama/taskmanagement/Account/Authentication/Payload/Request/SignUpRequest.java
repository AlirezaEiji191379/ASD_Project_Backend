package ir.rama.taskmanagement.Account.Authentication.Payload.Request;

import ir.rama.taskmanagement.Core.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest implements CrudRequest {

    private @NonNull String email;

    private @NonNull String password;
}
