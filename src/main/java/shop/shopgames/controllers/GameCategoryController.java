package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.shopgames.entites.GameCategory;
import shop.shopgames.services.GameCategoryService;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class GameCategoryController {
    private final GameCategoryService gameCategoryService;
    @Autowired
    public GameCategoryController(GameCategoryService gameCategoryService) {
        this.gameCategoryService = gameCategoryService;
    }
    @GetMapping(path = {"/addGameCategory", "/editGameCategory",})
    public String showForm(Model m, @RequestParam(value = "idCategory", required=false, defaultValue="-1") Long idCategory) {
        m.addAttribute("gameCategory", gameCategoryService.gameCategoryForm(idCategory));
        return "formCategory";
    }
    @PostMapping(path = "saveCategory")
    public String procesForm(@Valid  @ModelAttribute("gameCategory") GameCategory gameCategory, BindingResult result) {
        if(result.hasErrors()){

            return "formCategory";
        }
        gameCategoryService.saveGameCategory(gameCategory);
        return "redirect:/GameCategories";
    }
    @PostMapping(path = {"/deleteGameCategory"})
    public String deleteCategory(@RequestParam(value = "idCategory", required=true) Long idCategory) {
        gameCategoryService.deleteGameCategoryById(idCategory);
        return "redirect:/GameCategories";
    }

    @GetMapping(path = {"/GameCategories"})
    public String showCategories(Model m, @RequestParam(value = "idCategory", required=false, defaultValue="-1") Long idCategory) {
        m.addAttribute("gameCategory", gameCategoryService.gameCategoryForm(idCategory));
        return "formCategory";
    }
    @ModelAttribute("categories")
    public ArrayList<GameCategory> loadGameCategoryList() {
        return (ArrayList<GameCategory>) gameCategoryService.getCategory();
    }

}
