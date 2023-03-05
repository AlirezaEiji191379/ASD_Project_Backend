package ir.rama.taskmanagement.Task.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
