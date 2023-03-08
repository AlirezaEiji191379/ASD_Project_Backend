package ir.rama.taskmanagement.Task;

import ir.rama.taskmanagement.Task.DataAccessLayer.Repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskRepository taskRepository;

    public void deleteAllTasks(List<Integer> taskIds) {
        taskRepository.deleteAllById(taskIds);
    }
}
