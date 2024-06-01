package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gamePlatforms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GamePlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlatform;
    @NotEmpty(message = "Nazwa nie może być pusta")
    private String name;
    @NotNull
    private boolean digital=false;
    @NotNull
    private boolean visible =true;
    public String toString() {
        return this.getIdPlatform()+"-"+this.isVisible()+"-"+this.getName()+"-"+this.isDigital();
    }

}
