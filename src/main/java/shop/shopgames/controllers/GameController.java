package shop.shopgames.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.shopgames.GamePlatformFormatter;
import shop.shopgames.entites.*;
import shop.shopgames.GameCategoryFormatter;
import shop.shopgames.services.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GameController {
    private final GameService gameService;
    private final GameCategoryService gameCategoryService;
    private final GameTagsService gameTagsService;
    private final GamePlatformService gamePlatformService;
    private final OpinionsService opinionsService;
    private final OrderService orderService;

    @Autowired
    public GameController(GameService gameService, GameCategoryService gameCategoryService, GameTagsService gameTagsService, GamePlatformService gamePlatformService,OpinionsService opinionsService, OrderService orderService) {
        this.gameCategoryService=gameCategoryService;
        this.gameService = gameService;
        this.gameTagsService=gameTagsService;
        this.gamePlatformService=gamePlatformService;
        this.opinionsService=opinionsService;
        this.orderService=orderService;
    }


    @InitBinder({"game", "gameFilter"})
    public void initBinder2(WebDataBinder binder) {
        binder.addCustomFormatter(new GameCategoryFormatter(), "gameCategory");
        binder.addCustomFormatter(new GamePlatformFormatter(), "gamePlatform");
    }


    @GetMapping("/game")
    public String detailsGame(Model model, @RequestParam(value = "ID", required = true) Long IDGame) {
        Optional<Game> gameOptional = gameService.getGameById(IDGame);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            model.addAttribute("game", game);
            model.addAttribute("opinion", opinionsService.newOpinion());
            model.addAttribute("opinions", opinionsService.getOpinions(game.getIdGame()));
            model.addAttribute("isMyGame",orderService.isMyGame(IDGame));
            model.addAttribute("inMyBasket",orderService.inMyBasket(IDGame));
            model.addAttribute("basket",orderService.basketNew(game));
            return "detailsGame";
        } else {
            return "redirect:/error404";
        }
    }


    @PostMapping(path = "gameSave")
    public String procesForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("game") Game game, BindingResult result) {
        if(result.hasErrors()){
            System.out.println("test");
            System.out.println(result);
            if(game.getIdGame()==null && file.isEmpty()){
                FieldError customError = new FieldError("game", "fileName", "Brak okładki");
                result.addError(customError);
                return "formGame";
            }
            return "formGame";
        }
        if(game.getIdGame()==null && file.isEmpty()){
            FieldError customError = new FieldError("game", "fileName", "Brak okładki");
            result.addError(customError);
            return "formGame";
        }
        Long id =  gameService.saveGame(game, file);
        return "redirect:/game?ID="+id;
    }

    @GetMapping(path = {"/addGame", "editGame"})
    public String showForm(Model m, @RequestParam(value = "ID", required=false, defaultValue="-1") Long IDGame) {
        m.addAttribute("game", gameService.gameForm(IDGame));
        return "formGame";
    }
    @PostMapping(path = "deleteGame")
    public String deleteGame(@RequestParam(value = "ID", required=true) Long IDGame) {
        gameService.deleteGameById(IDGame);
        return "redirect:/";
    }

    @RequestMapping("/")
    public String gamesIndex(Model m){
        List<Game> games = gameService.getGames();
        m.addAttribute("games", games );
        return "index";
    }

    @GetMapping(path = "serach")
    public String serach(Model m, @ModelAttribute(value = "gameFilter") GameFilter gameFilter) {
        try {
            List<Game> games;
            if(!gameFilter.getMore()){
                games = gameService.findbyTitle(gameFilter.getTitle());
            }else {
                games = gameService.serach(gameFilter);
            }
            m.addAttribute("games", games );
            m.addAttribute("gameFilter", gameFilter );
        }catch (Exception e){
            return "index";
        }
        return "index";
    }


    @ModelAttribute("gameCategoryList")
    public ArrayList<GameCategory> loadGameGenreList() {return (ArrayList<GameCategory>) gameCategoryService.getCategory();}
    @ModelAttribute("gameTagsList")
    public ArrayList<GameTag> loadGameTagsList() {
        return (ArrayList<GameTag>) gameTagsService.getGamesTags();
    }
    @ModelAttribute("gamePlatformsList")
    public ArrayList<GamePlatform> loadGamePlatformList() {return (ArrayList<GamePlatform>) gamePlatformService.getGamesPlatforms();}
    @ModelAttribute("gameFilter")
    public GameFilter loadGameFilter() {GameFilter gameFilter = new GameFilter();return gameFilter;}



}