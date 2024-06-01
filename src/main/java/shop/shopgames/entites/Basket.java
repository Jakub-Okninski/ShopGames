package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kosz")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKosz;
    @NotNull
    @Min(0)
    private Integer quantity;
    @NotNull
    @Min(0)
    private Float price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idGame")
    @NotNull
    private Game game;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser")
    @NotNull
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idStatus")
    @NotNull
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idOrder", nullable = true)
    private Order order;

    public Basket(Game game, Integer quantity) {
        this.game=game;
        this.quantity=quantity;

    }



}
