package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "games")
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGame;
    @NotEmpty
    private String title;
    @NumberFormat(pattern = "#.00")
    @NotNull
    @Min(0)
    private Float price;
    @NotEmpty
    private String description;
    @Min(0)
    @NotNull
    private Float quantity;
    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate premiere;
    @NotNull
    private Boolean available=true;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gameCategoryId", nullable = false)
    private GameCategory gameCategory;
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<GameTag> GameTag;

    @Transient
    private String imageString;
    @NotNull
    private String fileName;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gamePlatformId", nullable = false)
    private GamePlatform gamePlatform;


    public Game(Long idGame, String title, Float price, String description, Float quantity, LocalDate premiere, Boolean available, GameCategory gameCategory, Set<shop.shopgames.entites.GameTag> gameTag, String fileName, GamePlatform gamePlatform) {
        this.idGame = idGame;
        this.title = title;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.premiere = premiere;
        this.available = available;
        this.gameCategory = gameCategory;
        GameTag = gameTag;
        this.fileName = fileName;
        this.gamePlatform = gamePlatform;

    }
}
