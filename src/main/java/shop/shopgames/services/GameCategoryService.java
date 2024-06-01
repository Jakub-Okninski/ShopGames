package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.GameCategory;
import shop.shopgames.repositories.GameCategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
public class GameCategoryService {
    private GameCategoryRepository gameCategoryRepository;

    @Autowired
    private GameCategoryService(GameCategoryRepository gameCategoryRepository) {
        this.gameCategoryRepository = gameCategoryRepository;
    }
    public List<GameCategory> getCategory() {
        return  gameCategoryRepository.findAllVisibleCategories();

    }
    public Optional<GameCategory> getGameCategoryById(Long IDGameCategory) {
        return gameCategoryRepository.findById(IDGameCategory);
    }
    public void saveGameCategory(GameCategory gameCategory) {
        gameCategoryRepository.save(gameCategory);
    }
    public void deleteGameCategoryById(Long IDGameCategory) {
        gameCategoryRepository.setVisibleToFalseByIdCategory(IDGameCategory);
    }
    public Optional<GameCategory> gameCategoryForm(Long IDGameCategory) {
        if (IDGameCategory!=-1) {
            return gameCategoryRepository.findById(IDGameCategory);
        } else {
            return Optional.of(new GameCategory());
        }
    }
}
