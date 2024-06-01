package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import shop.shopgames.AdresZamieszkaniaFormater;
import shop.shopgames.GameCategoryFormatter;
import shop.shopgames.GamePlatformFormatter;
import shop.shopgames.UserValidator;
import shop.shopgames.entites.AdresZamieszkania;
import shop.shopgames.entites.GameCategory;
import shop.shopgames.entites.Role;
import shop.shopgames.entites.User;
import shop.shopgames.services.UserServiceImpl;

import java.util.ArrayList;

@SessionAttributes
@Controller
public class UserController {
    private final UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new UserValidator());
    }

    @InitBinder("user")
    public void initBinder2(WebDataBinder binder) {
        binder.addCustomFormatter(new AdresZamieszkaniaFormater(), "adresZamieszkania");
    }


    @GetMapping("/register")
    public String register(Model m){
        m.addAttribute("user", new User());
        return "rejestracja";
    }

    @PostMapping(path = "/registerUser")
    public String procesForm(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            return "rejestracja";
        }
       User user1= userService.saveUser(user, Role.Types.ROLE_CLIENT, Role.Types.ROLE_CLIENT);

        if (user1.getAdresZamieszkania() == null) {
            user.setAdresZamieszkania(new AdresZamieszkania());
            user.setPassword("");
            user.setPasswordConfirm("");
            user.setUsername("");
            FieldError customError = new FieldError("user", "adresZamieszkania", "Zły format adresu. Poprawny format: ulica nr");
            result.addError(customError);
            return "rejestracja";

        }

        if (user1 == null) {
            user.setPassword("");
            user.setPasswordConfirm("");

            FieldError customError = new FieldError("user", "username", "Login lub Hasło jest już zajęte");
            result.addError(customError);
            return "rejestracja";

        }
        return "redirect:/";
    }

    @GetMapping("/pracownicy")
    public String pracownicy(Model m){
    m.addAttribute("user", new User());
    m.addAttribute("pracownicy", userService.getEmployees());
        return "pracownicy";
    }

    @PostMapping("/registerEmployee")
    public String registerEmployee(@Valid @ModelAttribute("user") User user, BindingResult result, Model m) {
        if (result.hasErrors()) {
            return "pracownicy";
        }


        User user1 = userService.saveUser(user,Role.Types.ROLE_SELLER, Role.Types.ROLE_CLIENT);

        if (user1.getAdresZamieszkania() == null) {
            user.setAdresZamieszkania(new AdresZamieszkania());
            user.setPassword("");
            user.setPasswordConfirm("");
            user.setUsername("");
            FieldError customError = new FieldError("user", "adresZamieszkania", "Zły format adresu. Poprawny format: ulica nr");
            result.addError(customError);
            return "pracownicy";

        }

        if (user1 == null) {
            user.setPassword("");
            user.setPasswordConfirm("");
            user.setUsername("");
            FieldError customError = new FieldError("user", "username", "Login lub Hasło jest już zajęte");
            result.addError(customError);

            return "pracownicy";

        }
        return "redirect:/";
    }


    @ModelAttribute("pracownicy")
    public ArrayList<User> loadGameGenreList() {return (ArrayList<User>) userService.getEmployees();}

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@ModelAttribute("idEmployee") Long id) {
        userService.deleteUser(id);

        return "redirect:/";
    }


}
