package shop.shopgames.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.shopgames.entites.Opinions;
import java.util.List;
public interface OpinionsRepository extends JpaRepository<Opinions, Long> {
    @Query("SELECT o FROM Opinions o WHERE o.game.idGame = ?1")
    List<Opinions> findOpinionsGame(Long gameId);
}

