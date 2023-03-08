package ir.rama.taskmanagement.Account.Authentication.Controller;

import ir.rama.taskmanagement.Account.Authentication.Payload.Request.EmailCheckRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.TokenValidationRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.SignInRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Request.SignUpRequest;
import ir.rama.taskmanagement.Account.Authentication.Service.AuthenticationService;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/sign-up")
    public ResponseEntity<CrudResponse> signUp(@RequestBody SignUpRequest request) {
        return service.signUp(request).getResponse();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<CrudResponse> signIn(@RequestBody SignInRequest request) {
        return service.signIn(request).getResponse();
    }

    @PostMapping("/validate-token")
    public ResponseEntity<CrudResponse> validateToken(@RequestBody TokenValidationRequest request) {
        return service.validateToken(request).getResponse();
    }

    @PostMapping("/check-email")
    public ResponseEntity<CrudResponse> checkEmail(@RequestBody EmailCheckRequest request) {
        return service.checkEmail(request).getResponse();
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Every thing is ok brother!");
    }

}
