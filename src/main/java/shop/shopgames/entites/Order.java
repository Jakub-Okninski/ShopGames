package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.util.Set;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;
    @NumberFormat(pattern = "#.00")
    @NotNull
    @Min(0)
    private Float price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser")
    @NotNull
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idStatus")
    @NotNull
    private Status status;
    @ManyToMany(fetch=FetchType.EAGER)
    @NotNull
    private Set<Basket> kosze;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idOrderDetails")
    @NotNull
    private OrderDetails orderDetails;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idPaymentTypes")
    @NotNull
    private PaymentTypes paymentTypes ;
}
