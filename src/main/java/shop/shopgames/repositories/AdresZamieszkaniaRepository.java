package shop.shopgames.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shop.shopgames.entites.AdresZamieszkania;
import shop.shopgames.entites.Game;
import shop.shopgames.entites.GameFilter;

import java.util.ArrayList;
import java.util.List;

public interface AdresZamieszkaniaRepository extends JpaRepository<AdresZamieszkania, Long> {

}
