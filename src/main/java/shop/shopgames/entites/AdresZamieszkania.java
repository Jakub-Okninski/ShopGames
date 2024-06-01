package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adresZamieszkania")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdresZamieszkania {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdresZamieszkania;
    @NotEmpty
    private String street;
    @NotEmpty
    private String houseNumber;

    @Override
    public String toString() {
        return this.getStreet()+" "+this.getHouseNumber();

    }
}
