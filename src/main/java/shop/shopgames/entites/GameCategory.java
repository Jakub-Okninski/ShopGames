package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GameCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;
    @NotNull
    private boolean visible = true;
    @NotEmpty
    private String name;
    @Override
    public String toString() {
        return this.getIdCategory()+"-"+this.isVisible()+"-"+this.getName();
    }

}
