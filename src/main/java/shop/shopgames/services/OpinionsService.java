package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.*;
import shop.shopgames.repositories.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class OpinionsService {
    private KoszRepository koszRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private PaymentTypesRepository paymentTypesRepository;
    private OrderRepository orderRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private BasketService basketService;
    private OpinionsRepository opinionsRepository;


    @Autowired
    private OpinionsService(KoszRepository koszRepository, GameRepository gameRepository, UserRepository userRepository, StatusRepository statusRepository, PaymentTypesRepository paymentTypesRepository, OrderRepository orderRepository, BasketService basketService, OrderDetailsRepository orderDetailsRepository,OpinionsRepository opinionsRepository) {
        this.koszRepository = koszRepository;
        this.gameRepository=gameRepository;
        this.userRepository=userRepository;
        this.statusRepository=statusRepository;
        this.paymentTypesRepository=paymentTypesRepository;
        this.orderRepository=orderRepository;
        this.basketService = basketService;
        this.orderDetailsRepository=orderDetailsRepository;
        this.opinionsRepository=opinionsRepository;
    }

       public List<Opinions> getOpinions(Long IDGame){
                List<Opinions> opinions = opinionsRepository.findOpinionsGame(IDGame);
           return opinions;
        }


    public Long deleteOpinion(Long ID){
      Optional<Opinions> o = opinionsRepository.findById(ID);
        opinionsRepository.deleteById(ID);
        return o.get().getGame().getIdGame();
    }

        public Opinions newOpinion(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user  = userRepository.findByUsername(username);
            Opinions o = new Opinions();
            o.setUser(user);
            o.setDate(LocalDate.now());
            return o;
        }

    public void addOpinions(Opinions opinions){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        opinions.setDate( LocalDate.now());
        User user = userRepository.findByUsername(username);
        opinions.setUser(user);
        opinionsRepository.save(opinions);
    }


}
