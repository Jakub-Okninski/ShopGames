package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.AdresZamieszkania;
import shop.shopgames.entites.Role;
import shop.shopgames.repositories.AdresZamieszkaniaRepository;
import shop.shopgames.repositories.RoleRepository;
import shop.shopgames.repositories.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AdresZamieszkaniaRepository adresZamieszkaniaRepository;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,AdresZamieszkaniaRepository adresZamieszkaniaRepository){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.adresZamieszkaniaRepository=adresZamieszkaniaRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        System.out.println("test");
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }


        return convertToUserDetails(user);
    }

    public List<shop.shopgames.entites.User> getEmployees(){
     List<Role> role =roleRepository.findRole2(Role.Types.ROLE_SELLER, Role.Types.ROLE_BOSS);
        Role r=role.get(0);
        Role r2=role.get(1);
        return userRepository.getEmployees(r,r2);
    }


    private User convertToUserDetails(shop.shopgames.entites.User user) {
        //mapujemy/transformujemy Role na Authority

        var grantedAuthorities = user.getRoles().stream().
                map(x-> new SimpleGrantedAuthority(x.getType().toString())).
                collect(Collectors.toSet());


        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);//UWAGA: klasa ma też drugi konstruktor – więcej parametrów, być może dobrze byłoby go wykorzystać!!!
    }


    public shop.shopgames.entites.User saveUser(shop.shopgames.entites.User user, Role.Types roleType,Role.Types roleType2) {
        try {
            String password= user.getPassword();
            user.setPassword( new BCryptPasswordEncoder().encode(password));

            List<Role> role =roleRepository.findRole2(roleType, roleType2);
            if(role.size()==2){
                Role r=role.get(0);
                Role r2=role.get(1);
                user.setRoles(new HashSet<>(Arrays.asList(r,r2)));

            }else{
                Role r=role.get(0);
                user.setRoles(new HashSet<>(Arrays.asList(r)));
            }

            System.out.println("Adres zamieszkania");

            AdresZamieszkania a = user.getAdresZamieszkania();
            System.out.println(a);
            shop.shopgames.entites.User userspr = userRepository.findByUsername(user.getUsername());

            if (userspr == null) {
                if (a == null) {
                    user.setAdresZamieszkania(null);
                    return user;
                }
                if(a.getStreet()==null&&a.getHouseNumber()==null&&a.getIdAdresZamieszkania()==null){
                    user.setAdresZamieszkania(null);
                    return user;
                }
                user.setAdresZamieszkania(adresZamieszkaniaRepository.save(a));
            }else {
                System.out.println("Istnijej taki użytkownik");
                return null;
            }

           return userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            System.out.println(e);
            System.out.println("Bład Istnijej taki użytkownik");
            return null;
        }

    }

    public Long getUserID (String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userauthentication = authentication.getName();
        if(userauthentication.equals(username)){
            return userRepository.findByUsername(username).getId();
        }
        return -1L;
    }

    public void deleteUser (Long id){
        userRepository.deleteById(id);
    }

}

