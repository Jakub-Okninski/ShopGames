package shop.shopgames.services;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.Basket;
import shop.shopgames.entites.Game;
import shop.shopgames.entites.Status;
import shop.shopgames.entites.User;
import shop.shopgames.repositories.GameRepository;
import shop.shopgames.repositories.KoszRepository;
import shop.shopgames.repositories.StatusRepository;
import shop.shopgames.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private KoszRepository koszRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private FileService fileService;


    @Autowired
    private BasketService(KoszRepository koszRepository, GameRepository gameRepository, UserRepository userRepository, StatusRepository statusRepository, FileService fileService) {
        this.koszRepository = koszRepository;
        this.gameRepository=gameRepository;
        this.userRepository=userRepository;
        this.statusRepository=statusRepository;
        this.fileService=fileService;
    }

        public Float allPrice(Long UserID){
           List<Float> ceny = koszRepository.myBasketPriceGame(UserID ,Status.Types.W_koszu);
            Float suma = 0F;
            for (Float cema: ceny){
                suma=suma+cema;
            }
            return suma;
        }
        public Basket aktualizujIlosc(Long koszykID, int quantity){
            Optional<Basket> koszOptional = koszRepository.findById(koszykID);

            if (koszOptional.isPresent()) {
                Basket basket = koszOptional.get();
                if(!basket.getGame().getAvailable()){
                    return basket;
                }
                int oldQuantity = basket.getQuantity();
                Game gameMy = basket.getGame();

                int buf = oldQuantity-quantity;

                try {
                    gameRepository.updateQuantity(gameMy.getIdGame(),buf);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                    return basket;
                }
                koszRepository.updateQuantity(basket.getIdKosz(),quantity);

                return basket;
            }
            return null;
        }
    public Basket dodajDoKosza(Long gameID, Integer quantity) {
        Optional<Game> optionalGame = gameRepository.findById(gameID);

        System.out.println("ID"+gameID);
        System.out.println("quantity"+quantity);

        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            Basket basket = new Basket();
            basket.setGame(game);
            basket.setQuantity(quantity);

            basket.setPrice(basket.getGame().getPrice()*quantity);

            System.out.println("dodano do kosz agre");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Status s = statusRepository.findStatus(Status.Types.W_koszu);
            basket.setStatus(s);
            if (user != null) {
                basket.setUser(user);
                System.out.println(user.getUsername());
                koszRepository.save(basket);
                System.out.println("Dodano do kosza: " + game.getTitle() + " - " + quantity);

                gameRepository.updateQuantity(game.getIdGame(),-quantity);
                return basket;
            } else {
                System.out.println("Nie można znaleźć użytkownika o nazwie: " + username);
            }
        } else {
            System.out.println("Nie można znaleźć gry o ID: " + gameID);
        }

        return null;
    }
    public List<Basket> mojKosz (Long ID){
      var mojKoszyk = koszRepository.myBasket(ID, Status.Types.W_koszu);
        for (Basket k : mojKoszyk) {
            k.getGame().setImageString(fileService.getFile(k.getGame().getFileName()));
        }
        return mojKoszyk;
    }

    public String usunKosz(Long id){
        Optional<Basket> koszOptional = koszRepository.findById(id);
        if (koszOptional.isPresent()) {
            Basket basket = koszOptional.get();
            String  UserId = basket.getUser().getUsername();
            gameRepository.updateQuantity(basket.getGame().getIdGame(), basket.getQuantity());
            koszRepository.delete(basket);
            return UserId;

        }
        return "";
    }

}
