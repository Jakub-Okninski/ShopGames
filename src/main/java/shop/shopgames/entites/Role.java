package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Types type;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role(Types type){
        this.type = type;
    }
    public enum Types{

        ROLE_CLIENT,
        ROLE_SELLER,
        ROLE_BOSS,

    }


}
