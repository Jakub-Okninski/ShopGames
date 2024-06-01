package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detailsOrders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrderDetails;
    @NotEmpty
    private String city;
    @NotEmpty
    private String zipCode;
    @NotEmpty
    private String street;
    @NotEmpty
    private String houseNumber;
    @NotEmpty
    private String number;
    @NotEmpty
    @Email
    private String adressEmail;
}
