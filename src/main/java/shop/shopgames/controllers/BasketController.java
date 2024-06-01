package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.shopgames.entites.Basket;
import shop.shopgames.services.BasketService;
import shop.shopgames.services.UserServiceImpl;

@Controller
public class BasketController {

    private final BasketService basketService;
    private final UserServiceImpl userService;

    public BasketController(BasketService basketService, UserServiceImpl userService) {
        this.userService=userService;
        this.basketService = basketService;
    }

    @RequestMapping("/dodajDoKosza")
    public String dodajDoKosza(@Valid @ModelAttribute("basket") Basket basket, BindingResult result) {
        if(result.hasErrors()){
            FieldError fieldError = result.getFieldError("quantity");
            if (fieldError != null) {
                return "redirect:/game?ID=" + basket.getGame().getIdGame();
            }
        }
        if(basket.getGame().getQuantity()<basket.getQuantity()){
            return "redirect:/game?ID=" + basket.getGame().getIdGame();
        }
        int b = basket.getQuantity();
        Basket k  = basketService.dodajDoKosza(basket.getGame().getIdGame(),b);
        return "redirect:/kosz?User=" + k.getUser().getUsername();
    }

    @RequestMapping("/deleteKosz")
    public String deleteKosz( @RequestParam(value = "koszID", required = true) Long koszID) {
        String id = basketService.usunKosz(koszID);
        return "redirect:/kosz?User=" +id;
    }

    @PostMapping("/NewQuantity")
    public String NewQuantity(@RequestParam(value = "newQuantity") Integer newQuantity, @RequestParam(value = "idKoszyk") Long idKoszyk) {
        Basket k  = basketService.aktualizujIlosc(idKoszyk,newQuantity);
        return "redirect:/kosz?User=" + k.getUser().getUsername();
    }

    @RequestMapping("/kosz")
    public String dodajDoKosza(Model m ,@RequestParam(value = "User", required = false) String User) {
        Long userId =  userService.getUserID(User);
        if (userId != null) {
            m.addAttribute("kosz", basketService.mojKosz(userId));
            m.addAttribute("allPrice", basketService.allPrice(userId));
            m.addAttribute("userId", userId);
            return "kosz";
        }
        else
            return "redirect:/";
    }


}
