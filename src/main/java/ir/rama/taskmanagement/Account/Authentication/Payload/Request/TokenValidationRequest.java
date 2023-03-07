package ir.rama.taskmanagement.Account.Authentication.Payload.Request;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationRequest implements CrudRequest {

    private @NonNull String token;
}
