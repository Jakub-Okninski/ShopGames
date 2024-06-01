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
import shop.shopgames.entites.GameCategory;
import shop.shopgames.entites.GamePlatform;
import shop.shopgames.entites.User;
import shop.shopgames.services.GameCategoryService;
import shop.shopgames.services.GamePlatformService;

import java.util.ArrayList;

@Controller
public class GamePlatformController {
    private final GamePlatformService gamePlatformService;
    @Autowired
    public GamePlatformController(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    @GetMapping(path = {"/addGamePlatform", "/editGamePlatform",})
    public String showForm(Model m, @RequestParam(value = "idPlatform", required=false, defaultValue="-1") Long idPlatform) {
        System.out.println(idPlatform);
        m.addAttribute("gamePlatform", gamePlatformService.gameFormPlatfrom(idPlatform));
        return "formPlatform";
    }

    @PostMapping(path = "savePlatform")
    public String procesForm(@Valid @ModelAttribute("gamePlatform") GamePlatform gamePlatform, BindingResult result) {
        if(result.hasErrors()){

            System.out.println("");
            System.out.println(result);
            System.out.println("");

            return "formPlatform";
        }
        gamePlatformService.saveGamePlatfrom(gamePlatform);
        return "redirect:/GamePlatform";
    }
    @GetMapping(path = {"/GamePlatform"})
    public String GamePlatform(Model m, @RequestParam(value = "idPlatform", required=false, defaultValue="-1") Long idPlatform) {
        System.out.println(idPlatform);
        m.addAttribute("gamePlatform", gamePlatformService.gameFormPlatfrom(idPlatform));
        return "formPlatform";
    }


    @PostMapping(path = {"/deleteGamePlatform"})
    public String deleteCategory(@RequestParam(value = "idPlatform", required=true) Long idPlatfrom) {
        gamePlatformService.deleteGamePlatformById(idPlatfrom);
        return "redirect:/GamePlatform";
    }
    @ModelAttribute("platforms")
    public ArrayList<GamePlatform> loadGameCategoryList() {
        return (ArrayList<GamePlatform>) gamePlatformService.getGamesPlatforms();
    }

}
