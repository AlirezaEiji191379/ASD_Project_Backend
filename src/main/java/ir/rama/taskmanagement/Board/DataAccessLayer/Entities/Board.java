package ir.rama.taskmanagement.Board.DataAccessLayer.Entities;

import ir.rama.taskmanagement.BoardColumn.DataAccessLayer.Entities.BoardColumn;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardColumn> columns;
}
