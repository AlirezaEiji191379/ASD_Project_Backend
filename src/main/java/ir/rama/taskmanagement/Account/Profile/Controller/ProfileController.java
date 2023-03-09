package ir.rama.taskmanagement.Account.Profile.Controller;

import ir.rama.taskmanagement.Account.Profile.Payload.Request.ProfileRequest;
import ir.rama.taskmanagement.Account.Profile.Service.ProfileService;
import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Response.ReponseBody.CrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PutMapping
    @CrossOrigin
    public ResponseEntity<CrudResponse> update(@RequestBody ProfileRequest request) {
        return service.update(request).getResponse();
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<CrudResponse> read(CrudRequest request) {
        return service.read(request).getResponse();
    }
}
