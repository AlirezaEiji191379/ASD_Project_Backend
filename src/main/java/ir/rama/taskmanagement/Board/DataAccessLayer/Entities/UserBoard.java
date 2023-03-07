package ir.rama.taskmanagement.Board.DataAccessLayer.Entities;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_boards")
public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "board_id")
    private Board board;
}
