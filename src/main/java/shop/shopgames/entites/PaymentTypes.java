package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "paymentTypes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaymentTypes;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Types type;
    public PaymentTypes(Types type) {
        this.type = type;
    }

    public enum Types {
        Blik,
        Przelew,
        Gotówka,

    }

    public String toString() {
        return this.getIdPaymentTypes()+"-"+this.getType();
    }
}
