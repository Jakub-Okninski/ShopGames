package shop.shopgames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.shopgames.entites.Status;
import java.util.List;
public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT s FROM Status s WHERE s.type = ?1")
    Status findStatus(Status.Types status);
    @Query("SELECT s FROM Status s WHERE s.type != ?1 and s.type != ?2 ")
    List<Status> findStatusNot(Status.Types status, Status.Types status2);
    @Query("SELECT s FROM Status s WHERE s.type != ?1 ")
    List<Status> findStatusNot(Status.Types status);

}

