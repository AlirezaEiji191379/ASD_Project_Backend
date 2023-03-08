package ir.rama.taskmanagement.Task.Controller;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import ir.rama.taskmanagement.Core.Payload.Response.ReponseBody.CrudResponse;
import ir.rama.taskmanagement.Task.Payload.Request.CreationRequest;
import ir.rama.taskmanagement.Task.Payload.Request.UpdateRequest;
import ir.rama.taskmanagement.Task.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CrudResponse> create(@RequestBody CreationRequest request) {
        return taskService.create(request).getResponse();
    }

    @GetMapping
    public ResponseEntity<CrudResponse> read(@RequestParam("task_id") Integer id, CrudRequest request) {
        return taskService.read(request, id).getResponse();
    }

    @PutMapping
    public ResponseEntity<CrudResponse> update(@RequestBody UpdateRequest request) {
        return taskService.update(request).getResponse();
    }

    @DeleteMapping
    public ResponseEntity<CrudResponse> delete(@RequestParam("task_id") Integer id, CrudRequest request) {
        return taskService.delete(request, id).getResponse();
    }
}
