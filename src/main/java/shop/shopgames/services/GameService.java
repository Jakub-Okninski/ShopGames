package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.shopgames.entites.*;
import shop.shopgames.repositories.GameRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GameService {
    private GameRepository gameRepository;
    private FileService fileService;


    @Autowired
    private GameService(GameRepository gameRepository, FileService fileService) {
        this.gameRepository = gameRepository;
        this.fileService = fileService;
    }
    public List<Game> getGames() {
        List<Game> games = gameRepository.findGameAllAvailable();


        for (Game ggg : games) {
//            String encodedString = Base64.getEncoder().encodeToString(ggg.getImageData());
//            ggg.setImageString(encodedString);
            ggg.setImageString(fileService.getFile(ggg.getFileName()));

        }




        return games;
    }

    public Optional<Game> getGameById(Long IDGame) {
        Optional<Game> g = gameRepository.findById(IDGame);

        try {
            // Sprawdź, czy gra została znaleziona przed kodowaniem danych obrazu
            if (g.isPresent()) {
//                String encodedString = Base64.getEncoder().encodeToString(g.get().getImageData());
//                g.get().setImageString(encodedString);


                g.get().setImageString(fileService.getFile(g.get().getFileName()));
            }
        } catch (Exception e) {
            // Tutaj możesz obsłużyć ewentualne błędy, na przykład zalogować informacje o nich
            e.printStackTrace();
        }

        return g;
    }
    public Long saveGame(Game g , MultipartFile file) {

//




        System.out.println("ID "+g.getIdGame());
        String oldFileName="";

        boolean flag = false;
        if(g.getIdGame()!=null && !file.isEmpty()){
         oldFileName = g.getFileName();
            flag=true;
        }
        if(!file.isEmpty()){
            String fileName = fileService.saveFile(file);
            g.setFileName(fileName);
        }



        Game savedGame = gameRepository.save(g);
        Long id = savedGame.getIdGame();
        System.out.println("ID "+g.getIdGame());

        if(flag){
            fileService.deleteFile(oldFileName);
        }
        return  id;
    }
    public void deleteGameById(Long IDGame) {

        gameRepository.updateAvailable(IDGame, false);
    }
    public Optional<Game> gameForm(Long IDGame) {
        if (IDGame!=-1) {
            Optional<Game> g = gameRepository.findById(IDGame);

          ///  game.setPremiere(LocalDate.parse(game.getPremiere()+"", DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            return g;
        } else {
            return Optional.of(new Game());
        }
    }

    public List<Game> serach(GameFilter gameFilter) {

        if(gameFilter.getTagF()){
            gameFilter.setGameTag(gameFilter.getTitle());
        }
        if (gameFilter.getGamePlatform() == null) {
            GamePlatform g = new GamePlatform();
           gameFilter.setGamePlatform(g);
        }
        if (gameFilter.getGameCategory() == null) {
            GameCategory g = new GameCategory();
            gameFilter.setGameCategory(g);
        }

        List<Game> games;
        if(gameFilter.getTagF()){
            games = gameRepository.searchGame(gameFilter);
        }else {
            games = gameRepository.searchGameWithoutTags(gameFilter);

        }

        for (Game ggg : games) {
            ggg.setImageString(fileService.getFile(ggg.getFileName()));
        }


        return games;
    }
    public List<Game> findbyTitle(String title) {
        List<Game> games = gameRepository.findByTitle(title);
        for (Game ggg : games) {
            ggg.setImageString(fileService.getFile(ggg.getFileName()));
        }
        return games;
    }



}
