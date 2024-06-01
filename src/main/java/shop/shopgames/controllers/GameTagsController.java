package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import shop.shopgames.entites.GamePlatform;
import shop.shopgames.entites.GameTag;
import shop.shopgames.services.GameTagsService;
import java.util.ArrayList;
import java.util.Optional;


@Controller
public class GameTagsController {
    private final GameTagsService gameTagsService;
    @Autowired
    public GameTagsController(GameTagsService gameTagsService) {
        this.gameTagsService = gameTagsService;
    }

    @PostMapping(path = "saveTag")
    public String procesForm(@Valid @ModelAttribute("gameTag") GameTag gameTag, BindingResult result) {
        if(result.hasErrors()){
            System.out.println(result);
            return "formTag";
        }
        gameTagsService.saveGameTag(gameTag);
        return "redirect:/GameTags";
    }

    @GetMapping(path = {"/addGameTag", "/editGameTag"})
    public String showForm(Model m, @RequestParam(value = "idTag", required=false, defaultValue="-1") Long gameIDTag) {
        m.addAttribute("gameTag", gameTagsService.gameFormTag(gameIDTag));
        return "formTag";
    }
    @PostMapping(path = {"/deleteGameTag"})
    public String deleteTag(@RequestParam(value = "idTag", required=true) Long idTag) {
             gameTagsService.deleteGameTagById(idTag);
        return "redirect:/GameTags";
    }
    @GetMapping(path = {"/GameTags"})
    public String GameTags(Model m, @RequestParam(value = "idTag", required=false, defaultValue="-1") Long gameIDTag) {
        m.addAttribute("gameTag", gameTagsService.gameFormTag(gameIDTag));
        return "formTag";
    }

    @ModelAttribute("tags")
    public ArrayList<GameTag> loadGameTagsList() {
        return (ArrayList<GameTag>) gameTagsService.getGamesTags();
    }

}
