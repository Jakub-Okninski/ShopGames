package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.shopgames.entites.Opinions;
import shop.shopgames.entites.Order;
import shop.shopgames.entites.OrderDetails;
import shop.shopgames.entites.Status;
import shop.shopgames.services.GamePlatformService;
import shop.shopgames.services.OpinionsService;
import shop.shopgames.services.OrderService;
import shop.shopgames.services.UserService;

import java.util.ArrayList;

@Controller
public class OpinionsController {
    private final OpinionsService opinionsService;
    private final UserService userService;

    @Autowired
    public OpinionsController(OpinionsService opinionsService, UserService userService) {
        this.opinionsService = opinionsService;
        this.userService=userService;
    }

    @PostMapping("/addOpinion")
    public String addOpinion(@Valid @ModelAttribute(value = "opinion") Opinions opinions, BindingResult result) {
        if(result.hasErrors()){
            System.out.println(result);
            return "redirect:/game?ID="+opinions.getGame().getIdGame();
        }
        System.out.println("Comment: " + opinions.getComment());

        System.out.println("lalal");
        System.out.println(opinions.getGame().getTitle());
        opinionsService.addOpinions(opinions);
        return "redirect:/game?ID="+opinions.getGame().getIdGame();

    }

    @GetMapping(path = "deleteOpinions")
    public String deleteOpinions(@RequestParam(value = "opinionID", required=true) Long opinionID) {
        Long ID = opinionsService.deleteOpinion(opinionID);
        return "redirect:/game?ID="+ID;
    }
}
