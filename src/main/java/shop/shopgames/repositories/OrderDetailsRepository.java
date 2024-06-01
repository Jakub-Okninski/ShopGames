package shop.shopgames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopgames.entites.OrderDetails;
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

}

