package ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities;

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
@Table(name = "tokens")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  @Column(name = "token", unique = true, nullable = false)
  public String token;

  @Column(name = "expired", nullable = false)
  public boolean expired;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  public User user;
}
