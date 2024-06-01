package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "status")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Types type;
    public Status(Types type){
        this.type = type;
    }
    public enum Types{
        W_koszu,
        Zamówiono,
        W_realizacji,
        Dostarczono,
    }


}
