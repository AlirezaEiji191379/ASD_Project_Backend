package ir.rama.taskmanagement.Account.Profile.Payload.Request;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest implements CrudRequest {

    private @Nullable String newUsername;

    private @Nullable String displayName;

    private @Nullable String password;
}
