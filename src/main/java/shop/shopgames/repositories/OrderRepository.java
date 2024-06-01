package shop.shopgames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shop.shopgames.entites.Order;
import shop.shopgames.entites.Status;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_Id(Long userId);

    @Query("SELECT o FROM Order o WHERE o.status.type NOT IN (?1, ?2)")
    List<Order> findOrdersWithoutStatus(Status.Types status,Status.Types status2);
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status.id = :statusId WHERE o.idOrder = :idOrder")
    void updateStatusOrderByID(Long idOrder, Long statusId);



}

