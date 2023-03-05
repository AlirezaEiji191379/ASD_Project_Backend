package ir.rama.taskmanagement.Task.DataAccessLayer.Repositories;

import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {

    @Query("select u from UserTask u where u.user in (?1)")
    List<UserTask> findByUserIds(List<Integer> userIds);
}
