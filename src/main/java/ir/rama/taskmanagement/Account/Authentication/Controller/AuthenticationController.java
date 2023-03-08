package ir.rama.taskmanagement.Account.Authentication.Controller;

import ir.rama.taskmanagement.Account.Authentication.Payload.AuthenticationRequest;
import ir.rama.taskmanagement.Account.Authentication.Payload.Response.Response;
import ir.rama.taskmanagement.Account.Authentication.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/sign-up")
    public ResponseEntity<Response> signUp(
            @RequestBody AuthenticationRequest request
    ) {
        return service.signUp(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response> signIn(
            @RequestBody AuthenticationRequest request
    ) {
        return service.signIn(request);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Every thing is ok brother!");
    }

}
