package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "opinions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Opinions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOpinions;
    @NotEmpty
    private String comment;
    @Min(1) @Max(5)
    private Integer stars;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gameId")
    private Game game;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="udserId")
    private User user;
    @NotNull
    private LocalDate date;
}
