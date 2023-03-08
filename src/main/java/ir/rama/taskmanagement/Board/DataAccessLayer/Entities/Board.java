package ir.rama.taskmanagement.Board.DataAccessLayer.Entities;

import ir.rama.taskmanagement.Column.DataAccessLayer.Entities.Column;
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

    @jakarta.persistence.Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Column> columns;
}
