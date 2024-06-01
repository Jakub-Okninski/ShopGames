package shop.shopgames;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.shopgames.entites.*;
import shop.shopgames.repositories.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Configuration
public class RepositoriesInitializer {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GameTagsRepository gameTagsRepository;
    @Autowired
    private GamePlatformRepository gamePlatformRepository;
    @Autowired
    private GameCategoryRepository gameCategoryRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PaymentTypesRepository paymentTypesRepository;
    @Autowired
    private AdresZamieszkaniaRepository adresZamieszkaniaRepository;
    @Bean
    InitializingBean init() {
        return () -> {
            if(paymentTypesRepository.findAll().isEmpty()) {
                paymentTypesRepository.save(new PaymentTypes(PaymentTypes.Types.Gotówka));
                paymentTypesRepository.save(new PaymentTypes(PaymentTypes.Types.Przelew));
                paymentTypesRepository.save(new PaymentTypes(PaymentTypes.Types.Blik));
            }
            if(gameTagsRepository.findAll().isEmpty()) {


                GameTag gt1= new  GameTag(1L,"Strzelanki", true);
                GameTag gt2= new  GameTag(2L,"Broń", true);
                GameTag gt3= new  GameTag(3L,"Akcja", true);
                GameTag gt4= new  GameTag(4L,"+16", true);
                GameTag gt5= new  GameTag(5L,"Rolnictwo", true);
                GameTag gt6= new  GameTag(6L,"Piłka", true);


                gameTagsRepository.save(gt1);
                gameTagsRepository.save(gt2);
                gameTagsRepository.save(gt3);
                gameTagsRepository.save(gt4);
                gameTagsRepository.save(gt5);
                gameTagsRepository.save(gt6);




                GamePlatform gp1 = new GamePlatform(1L, "PC", false,true);
                GamePlatform gp2 = new GamePlatform(2L, "PS5", false,true);
                GamePlatform gp3 = new GamePlatform(3L, "PS4", false,true);
                GamePlatform gp4 = new GamePlatform(4L, "PC - key", true,true);
                GamePlatform gp5 = new GamePlatform(5L, "PS5 - key", true,true);
                gamePlatformRepository.save(gp1);
                gamePlatformRepository.save(gp2);
                gamePlatformRepository.save(gp3);
                gamePlatformRepository.save(gp4);
                gamePlatformRepository.save(gp5);


                GameCategory gc1=new GameCategory(1L,true,"Symulator");
                GameCategory gc2=new GameCategory(2L,true,"Akcja");
                GameCategory gc3=new GameCategory(3L,true,"Sportowe");
                GameCategory gc4=new GameCategory(4L,true,"Fantazy");
                GameCategory gc5=new GameCategory(5L,true,"Wyscigowe");

                gameCategoryRepository.save(gc1);
                gameCategoryRepository.save(gc2);
                gameCategoryRepository.save(gc3);
                gameCategoryRepository.save(gc4);
                gameCategoryRepository.save(gc5);



                LocalDate date1= LocalDate.of(2018, 11, 22);
                LocalDate date2 = LocalDate.of(2021, 9, 27);
                LocalDate date3 = LocalDate.of(2013, 9, 17);
                LocalDate date4 = LocalDate.of(2015, 5, 18 );

                Set<GameTag> gamesTags = new HashSet<>();
                Set<GameTag> gamesTags2 = new HashSet<>();
                Set<GameTag> gamesTags3 = new HashSet<>();
                Set<GameTag> gamesTags4 = new HashSet<>();

                gamesTags.add(gt5);
                gamesTags2.add(gt6);
                gamesTags3.add(gt2);
                gamesTags3.add(gt4);
                gamesTags4.add(gt4);
                Game g1= new Game(1L,"Farming Symulator 2019", 100F, "Symulator Farmy",20F ,date1,true,gc1,gamesTags,"farmin19.jpg",gp1);
                gameRepository.save(g1);
                Game g2= new Game(2L,"Fifa 2022", 220F, "Symulator Piłki",25F ,date2,true,gc3,gamesTags2,"fifa22.jpg",gp2);
                gameRepository.save(g2);
                Game g3= new Game(3L,"GTA V", 50F, "Symulator Misata",50F ,date3,true,gc2,gamesTags3,"gtav.jpg",gp3);
                gameRepository.save(g3);
                Game g4= new Game(4L,"Wiedzmin 3", 40F, "Symulator Wiedzmina",10F ,date4,true,gc4,gamesTags4,"wiedzmin3.jpg",gp5);
                gameRepository.save(g4);

            }

            if(statusRepository.findAll().isEmpty()) {
                Status W_koszu = statusRepository.save(new Status(Status.Types.W_koszu));
                Status W_realizacji = statusRepository.save(new Status(Status.Types.W_realizacji));
                Status Zamówiono = statusRepository.save(new Status(Status.Types.Zamówiono));
                Status Dostarczono = statusRepository.save(new Status(Status.Types.Dostarczono));
                statusRepository.save(W_koszu);
                statusRepository.save(W_realizacji);
                statusRepository.save(Zamówiono);
                statusRepository.save(Dostarczono);
            }

            if(roleRepository.findAll().isEmpty()) {
                Role roleSeller = roleRepository.save(new Role(Role.Types.ROLE_SELLER));
                Role roleClient = roleRepository.save(new Role(Role.Types.ROLE_CLIENT));
                Role roleBoss = roleRepository.save(new Role(Role.Types.ROLE_BOSS));

                AdresZamieszkania a = new AdresZamieszkania(1L,"Radomyśl","32");
                adresZamieszkaniaRepository.save(a);

                User user = new User("333", true);
                user.setRoles(new HashSet<>(List.of(roleClient)));
                user.setPassword(passwordEncoder.encode("3"));
                user.setPasswordConfirm(passwordEncoder.encode("3"));
                user.setEmail("Klient@gmail.com");
                user.setName("Jan");
                user.setLastname("Kowalski");
                user.setAdresZamieszkania(a);
                userRepository.save(user);

                user = new User("222", true);
                user.setRoles(new HashSet<>(Arrays.asList(roleSeller,roleClient)));
                user.setPassword(passwordEncoder.encode("2"));
                user.setPasswordConfirm(passwordEncoder.encode("2"));
                user.setEmail("Klient@gmail.com");
                user.setName("Jan");
                user.setLastname("Kowalski");
                a = new AdresZamieszkania(2L,"Radomyśl","32");
                adresZamieszkaniaRepository.save(a);


                user.setAdresZamieszkania(a);
                userRepository.save(user);

                user = new User("111", true);
                user.setRoles(new HashSet<>(Arrays.asList(roleBoss,roleSeller,roleClient)));
                user.setPassword(passwordEncoder.encode("1"));
                user.setPasswordConfirm(passwordEncoder.encode("1"));
                user.setEmail("Klient@gmail.com");
                user.setName("Jan");
                user.setLastname("Kowalski");
                a = new AdresZamieszkania(3L,"Radomyśl","32");
                adresZamieszkaniaRepository.save(a);


                user.setAdresZamieszkania(a);
                userRepository.save(user);

            }
        };
    }
    private byte[] tablicaByte(String filePath) {
        byte[] fileBytes = null;  // Inicjalizuję tablicę na zewnątrz bloku try

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            fileBytes = new byte[(int) new File(filePath).length()];
            fis.read(fileBytes);

            // Tutaj możesz wykonywać operacje na tablicy bajtów (fileBytes)
            System.out.println("Pomyślnie odczytano plik bajtów.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileBytes;
    }




}