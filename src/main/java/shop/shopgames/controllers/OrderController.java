package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import shop.shopgames.OrderDetailsValidator;
import shop.shopgames.UserValidator;
import shop.shopgames.entites.*;
import shop.shopgames.services.OrderService;
import shop.shopgames.services.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final UserServiceImpl userService;

    public OrderController(OrderService orderService, UserServiceImpl userService ) {
        this.orderService = orderService;
        this.userService=userService;
    }

    @GetMapping("/zamowienie")
    public String zamowienie(Model m ,@RequestParam(value = "userId", required = false) Long userId) {
        m.addAttribute("orderDetails", orderService.getOrderDetails(userId));
        m.addAttribute("price", orderService.getAllPrice(userId));
        m.addAttribute("user", orderService.getUserOrder(userId));
        m.addAttribute("kosze",orderService.getKosze(userId));
        return "order";
    }

    @InitBinder("orderDetails")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new OrderDetailsValidator());

    }

    @GetMapping("/order")
    public String zamawiam(@Valid @ModelAttribute(value = "orderDetails") OrderDetails orderDetails, BindingResult result, @RequestParam(value = "idPaymentTypes", required = false) Long idPaymentTypes, Model m) {
        System.out.println(orderDetails.getCity());
       if(result.hasErrors()){
           return "order";
       }
        Order order = orderService.saveOrder( orderDetails,  idPaymentTypes);
        return "redirect:/myOrders?User=" + order.getUser().getUsername();
    }





    @GetMapping("/myOrders")
    public String zamawiam(Model m , @RequestParam(value = "User", required = false) String User ) {

        m.addAttribute("orders",orderService.getOrderUser(User));
        return "myOrders";
    }
    @ModelAttribute("PaymentTypes")
    public List<PaymentTypes> PaymentTypes() {
        return orderService.gatPayment();
    }

    @GetMapping("/ordersPanel")
    public String ordersPanel(Model m) {
        m.addAttribute("orders",orderService.getOrderPanel());
        return "ordersPanel";
    }
    @PostMapping("/aktualizujStatus")
    public String ordersPanel(@RequestParam(value = "idStatus", required = false) Long idStatus, @RequestParam(value = "idOrder", required = false) Long idOrder) {
        orderService.updateOrderStatus(idOrder, idStatus);
        return "redirect:/ordersPanel";
    }
    @ModelAttribute("statusPanel")
    public ArrayList<Status> statusPanel() {
        ArrayList<Status> status = (ArrayList<Status>) orderService.getStatusPanel();
        System.out.println(status);
        return status;
    }

    @ModelAttribute("kosze")
    public List<Basket> kosze() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserID(username);
        return orderService.getKosze(userId);
    }

    @ModelAttribute("user")
    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserID(username);
        return orderService.getUserOrder(userId);
    }
    @ModelAttribute("price")
    public Double price() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserID(username);
        return orderService.getAllPrice(userId);
    }
    @ModelAttribute("orderDetails")
    public OrderDetails orderDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserID(username);
        return orderService.getOrderDetails(userId);
    }







}
