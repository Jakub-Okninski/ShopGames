package shop.shopgames.repositories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shop.shopgames.entites.GamePlatform;
import java.util.List;
public interface GamePlatformRepository extends JpaRepository<GamePlatform, Long>{
    @Transactional
    @Modifying
    @Query("UPDATE GamePlatform g SET g.visible = false WHERE g.idPlatform = ?1")
    void setVisibleToFalseByIdPlatfrom(Long idPlatform);
    @Query("SELECT g FROM GamePlatform g WHERE g.visible = true ORDER BY g.idPlatform ASC")
    List<GamePlatform> findAllVisiblePlatforms();


}
