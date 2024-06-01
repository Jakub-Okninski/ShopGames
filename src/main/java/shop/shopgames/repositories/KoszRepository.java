package shop.shopgames.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shop.shopgames.entites.Basket;
import shop.shopgames.entites.Status;
import java.util.List;
public interface KoszRepository extends JpaRepository<Basket, Long> {

    @Query("SELECT b FROM Basket b WHERE b.user.id = :userId AND b.status.type = :status")
    List<Basket> myBasket (Long userId, Status.Types status);
    @Query("SELECT b FROM Basket b WHERE b.user.id = :userId AND b.status.type = :status AND b.game.idGame=:gameId")
    List<Basket> isMyGame (Long userId, Long gameId, Status.Types status);

    @Query("SELECT SUM(b.price * b.quantity) FROM Basket b WHERE b.user.id = :userId AND b.status.type = :types")
    Double getAllPrice(Long userId, Status.Types types);


    @Query("SELECT b.quantity* b.game.price FROM Basket b WHERE b.user.id = :userId AND b.status.type = :status")
    List<Float> myBasketPriceGame (Long userId, Status.Types status);
    @Transactional
    @Modifying
    @Query("UPDATE Basket b SET b.quantity = :quantity WHERE b.idKosz = :idKosz")
    void updateQuantity(Long idKosz, Integer quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Basket b SET b.status.id = :statusId, b.order.idOrder = :zamowienieId WHERE b.user.id = :userId AND b.status.type = :status")
    void updateStatusAndOrderInBasket(Long userId, Integer statusId, Long zamowienieId, Status.Types status);
    @Transactional
    @Modifying
    @Query("UPDATE Basket b SET b.status.id = :status  WHERE b.order.idOrder=:idOrder")
    void updateStatusInBasketIDOrder(Long idOrder, Long status);

}



