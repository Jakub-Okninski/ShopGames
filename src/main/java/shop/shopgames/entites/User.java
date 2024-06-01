package shop.shopgames.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 36)
    @NotBlank
    private String name;
    @Size(min = 3, max = 36)
    @NotBlank
    private String lastname;
    @Email
    @NotBlank
    private String email;
    @Size(min = 3, max = 36)
    @Column(unique = true)
    @NotBlank
    private String username;
    @NotEmpty
    private String password;
    @Transient
    private String passwordConfirm;
    @AssertTrue
    private boolean enabled = true;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @OneToOne
    private AdresZamieszkania adresZamieszkania;

    public User(String username){
        this(username, false);
    }
    public User(String username, boolean enabled){
        this.username = username;
        this.enabled = enabled;
    }

}
