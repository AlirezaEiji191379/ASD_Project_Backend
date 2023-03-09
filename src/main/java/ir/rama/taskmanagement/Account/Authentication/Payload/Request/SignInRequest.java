package ir.rama.taskmanagement.Account.Authentication.Payload.Request;

import ir.rama.taskmanagement.Core.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest implements CrudRequest {

    private String email;

    private String password;
}
