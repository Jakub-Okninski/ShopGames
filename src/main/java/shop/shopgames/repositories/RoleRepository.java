package shop.shopgames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.shopgames.entites.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.type = ?1")
    Role findRole(Role.Types type);
    @Query("SELECT r FROM Role r WHERE r.type = ?1 or r.type = ?2")
    List<Role> findRole2(Role.Types type, Role.Types type2);

}

