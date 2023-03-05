package ir.rama.taskmanagement.Task;

import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.Task;
import ir.rama.taskmanagement.Task.DataAccessLayer.Repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private TaskRepository taskRepository;

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
