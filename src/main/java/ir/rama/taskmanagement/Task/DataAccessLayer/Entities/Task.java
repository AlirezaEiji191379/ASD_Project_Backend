package ir.rama.taskmanagement.Task.DataAccessLayer.Entities;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import ir.rama.taskmanagement.Column.DataAccessLayer.Entities.Column;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @jakarta.persistence.Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @jakarta.persistence.Column(name = "title", nullable = false)
    private String title;

    @jakarta.persistence.Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "column_id", nullable = false)
    private Column column;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @jakarta.persistence.Column(name = "deadline", columnDefinition = "TIMESTAMP")
    private LocalDateTime deadline;
}
