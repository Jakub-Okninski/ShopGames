package shop.shopgames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.shopgames.entites.Role;
import shop.shopgames.entites.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE :typ MEMBER OF u.roles AND :typ2 NOT MEMBER OF u.roles")
    List<User> getEmployees(@Param("typ") Role typ, @Param("typ2") Role typ2);



}



