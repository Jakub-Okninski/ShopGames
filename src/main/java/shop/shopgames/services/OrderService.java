package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.*;
import shop.shopgames.repositories.*;

import java.util.*;

@Service
public class OrderService {
    private KoszRepository koszRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private PaymentTypesRepository paymentTypesRepository;
    private OrderRepository orderRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private BasketService basketService;

    @Autowired
    private OrderService(KoszRepository koszRepository, GameRepository gameRepository, UserRepository userRepository, StatusRepository statusRepository, PaymentTypesRepository paymentTypesRepository, OrderRepository orderRepository, BasketService basketService, OrderDetailsRepository orderDetailsRepository) {
        this.koszRepository = koszRepository;
        this.gameRepository=gameRepository;
        this.userRepository=userRepository;
        this.statusRepository=statusRepository;
        this.paymentTypesRepository=paymentTypesRepository;
        this.orderRepository=orderRepository;
        this.basketService = basketService;
        this.orderDetailsRepository=orderDetailsRepository;
    }

    public List<PaymentTypes> gatPayment (){
        List<PaymentTypes> paymentTypes = paymentTypesRepository.findAll();
        return paymentTypes;
    }
    public List<Basket> getKosze (Long userId){
        List<Basket> kosze = koszRepository.myBasket(userId, Status.Types.W_koszu);
        return kosze;
    }
    public OrderDetails getOrderDetails (Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        OrderDetails orderDetails = new OrderDetails();
        if(optionalUser.isPresent()){
            User uesr = optionalUser.get();
            orderDetails.setAdressEmail(uesr.getEmail());
        }
       return orderDetails;
    }
    public User getUserOrder (Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User uesr = optionalUser.get();
            return uesr;
        }
        return null;
    }
    public Basket basketNew(Game game){
        Basket b = new Basket();
        b.setGame(game);
        b.setQuantity(1);
        return b;
    }

    public boolean inMyBasket (Long idGame){
        List<Basket> basketList;
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println(username);
            User user = userRepository.findByUsername(username);
            System.out.println(user.getId());
            System.out.println(idGame);

            basketList = koszRepository.isMyGame(user.getId(), idGame, Status.Types.W_koszu);
            System.out.println(basketList);
        }catch (Exception e){
            System.out.println("Bład" );
            return false;
        }

        if(!basketList.isEmpty()){
            System.out.println("moze");
            return true;
        }
        return false;
    }

    public boolean isMyGame (Long idGame){
        List<Basket> basketList;
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println(username);
            User user = userRepository.findByUsername(username);
            System.out.println(user.getId());
            System.out.println(idGame);

            basketList = koszRepository.isMyGame(user.getId(), idGame, Status.Types.Dostarczono);
            System.out.println(basketList);
        }catch (Exception e){
            System.out.println("Bład" );
            return false;
        }


        if(!basketList.isEmpty()){
            System.out.println("moze");
            return true;
        }


        return false;
    }
    public Order saveOrder (OrderDetails orderDetails, Long idPaymentTypes){
        System.out.println("dupa");
        System.out.println(orderDetails.toString());

        PaymentTypes paymentTypes = paymentTypesRepository.getReferenceById(idPaymentTypes);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         User user = userRepository.findByUsername(username);
         Order order = new Order();
         order.setUser(user);
        order.setPaymentTypes(paymentTypes);

        order.setPrice(basketService.allPrice(user.getId()));


        List<Basket> basketList = koszRepository.myBasket(user.getId(), Status.Types.W_koszu);

        for (Basket basket : basketList){
            basket.setPrice(basket.getGame().getPrice());
        }

// Utwórz nowy HashSet używając konstruktora, który przyjmuje kolekcję
        Set<Basket> basketSet = new HashSet<>(basketList);

        order.setKosze(basketSet);



        Status status =  statusRepository.findStatus(Status.Types.Zamówiono);
        order.setStatus(status);
      OrderDetails orderDetails1 = orderDetailsRepository.save(orderDetails);
        order.setOrderDetails(orderDetails1);

        Order orderSave =  orderRepository.save(order);

        koszRepository.updateStatusAndOrderInBasket(user.getId(), status.getId(), orderSave.getIdOrder(), Status.Types.W_koszu);
        return orderSave;
    }
    public List<Order> getOrderUser (String user){
        Long userId = userRepository.findByUsername(user).getId();
        List<Order> Orders = orderRepository.findAllByUser_Id(userId);
        return Orders;
    }

    public List<Order> getOrderPanel(){
        List<Order> Orders = orderRepository.findOrdersWithoutStatus(Status.Types.W_koszu, Status.Types.Dostarczono);
        return Orders;
    }
    public List<Status> getStatusPanel(){
        List<Status> ss =  statusRepository.findStatusNot(Status.Types.W_koszu);
        return ss;
    }

    public void updateOrderStatus(Long idOrder, Long idSatus){
        koszRepository.updateStatusInBasketIDOrder(idOrder, idSatus);
        orderRepository.updateStatusOrderByID(idOrder,idSatus);
    }
    public Double getAllPrice(Long idUser){
        return koszRepository.getAllPrice(idUser, Status.Types.W_koszu);
    }



}
