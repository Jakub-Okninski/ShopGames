package shop.shopgames.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shop.shopgames.entites.Game;
import shop.shopgames.entites.GameFilter;

import java.util.ArrayList;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.quantity = g.quantity + :quantity WHERE g.idGame = :idGame")
    void updateQuantity(Long idGame, Integer quantity);

    @Query("select g from Game g where UPPER(g.title) LIKE UPPER(CONCAT('%', :tytul, '%')) and g.available=true")
    ArrayList<Game> findByTitle(String tytul);
    @Query("SELECT g FROM Game g WHERE" +
            "(:#{#filter.title} IS NULL OR UPPER(g.title) LIKE UPPER(CONCAT('%', :#{#filter.title}, '%'))) AND " +
            "(:#{#filter.priceMin} IS NULL OR g.price >= :#{#filter.priceMin}) AND" +
            "(:#{#filter.priceMax} IS NULL OR g.price <= :#{#filter.priceMax}) AND" +
            "(:#{#filter.dataMin} IS NULL OR g.premiere >= :#{#filter.dataMin}) AND" +
            "(:#{#filter.dataMax} IS NULL OR g.premiere <= :#{#filter.dataMax}) AND" +
            "(:#{#filter.gameCategory.idCategory} IS NULL OR g.gameCategory.idCategory = :#{#filter.gameCategory.idCategory}) AND" +
            "(:#{#filter.gamePlatform.idPlatform} IS NULL OR g.gamePlatform.idPlatform = :#{#filter.gamePlatform.idPlatform}) AND " +
            " g.available=true"
    )
    List<Game> searchGameWithoutTags(@Param("filter") GameFilter filter);


    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.available = :available WHERE g.idGame = :idGame")
    void updateAvailable(Long idGame, Boolean available);

    @Query("select g from Game g where g.available=true")
    ArrayList<Game> findGameAllAvailable();
    @Query("SELECT DISTINCT g FROM Game g " +
            "JOIN g.GameTag t " +
            "WHERE" +
            "(:#{#filter.title} IS NULL OR UPPER(g.title) LIKE UPPER(CONCAT('%', :#{#filter.title}, '%'))OR UPPER(t.name) LIKE UPPER(CONCAT('%', :#{#filter.title}, '%'))) AND" +
            "(:#{#filter.priceMin} IS NULL OR g.price >= :#{#filter.priceMin}) AND" +
            "(:#{#filter.priceMax} IS NULL OR g.price <= :#{#filter.priceMax}) AND" +
            "(:#{#filter.dataMin} IS NULL OR g.premiere >= :#{#filter.dataMin}) AND" +
            "(:#{#filter.dataMax} IS NULL OR g.premiere <= :#{#filter.dataMax}) AND" +
            "(:#{#filter.gameCategory.idCategory} IS NULL OR g.gameCategory.idCategory = :#{#filter.gameCategory.idCategory}) AND" +
            "(:#{#filter.gamePlatform.idPlatform} IS NULL OR g.gamePlatform.idPlatform = :#{#filter.gamePlatform.idPlatform}) AND" +
            " g.available=true"
    )
    List<Game> searchGame(@Param("filter") GameFilter filter);
}
