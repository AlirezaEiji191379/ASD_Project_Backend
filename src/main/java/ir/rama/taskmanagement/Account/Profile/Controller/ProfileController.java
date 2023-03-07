package ir.rama.taskmanagement.Account.Profile.Controller;

import ir.rama.taskmanagement.Account.Profile.Payload.Request.ProfileRequest;
import ir.rama.taskmanagement.Account.Profile.Service.ProfileService;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody ProfileRequest request) {
        return service.update(request).getResponse();
    }

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestParam(name = "user_id") Integer userId) {
        return service.read(userId).getResponse();
    }
}
