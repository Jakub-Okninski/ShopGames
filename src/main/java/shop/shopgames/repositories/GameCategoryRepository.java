package shop.shopgames.repositories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shop.shopgames.entites.GameCategory;
import java.util.List;
public interface GameCategoryRepository extends JpaRepository<GameCategory, Long>{
    @Transactional
    @Modifying
    @Query("UPDATE GameCategory g SET g.visible = false WHERE g.idCategory = ?1")
    void setVisibleToFalseByIdCategory(Long idTag);
    @Query("SELECT g FROM GameCategory g WHERE g.visible = true ORDER BY g.idCategory ASC")
    List<GameCategory> findAllVisibleCategories();

}
