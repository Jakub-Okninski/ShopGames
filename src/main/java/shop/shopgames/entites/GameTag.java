package shop.shopgames.entites;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTag;
    @NotEmpty(message = "Nazwa nie może być pusta")
    private String name;
    @NotNull
    private boolean visible = true;


}
