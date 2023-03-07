package ir.rama.taskmanagement.Task.DataAccessLayer.Entities;

import ir.rama.taskmanagement.Account.User.DataAccessLayer.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users_tasks",
        indexes = @Index(name = "unique_index__user_id__task_id", columnList = "user_id, task_id", unique = true)
)
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToOne
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "task_id")
    private Task task;
}
