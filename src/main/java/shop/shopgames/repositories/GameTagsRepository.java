package shop.shopgames.repositories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shop.shopgames.entites.GameTag;
import java.util.List;

public interface GameTagsRepository extends JpaRepository<GameTag, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE GameTag g SET g.visible = false WHERE g.idTag = ?1")
    void setVisibleToFalseByIdTag(Long idTag);
    @Query("SELECT g FROM GameTag g WHERE g.visible = true ORDER BY g.idTag ASC")
    List<GameTag> findAllVisibleTags();

}
