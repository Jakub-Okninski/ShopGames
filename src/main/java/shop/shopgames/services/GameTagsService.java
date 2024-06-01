package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.GameTag;
import shop.shopgames.repositories.GameTagsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameTagsService {
    private GameTagsRepository gameTagsRepository;

    @Autowired
    private GameTagsService(GameTagsRepository gameTagsRepository) {
        this.gameTagsRepository = gameTagsRepository;
    }
    public List<GameTag> getGamesTags() {
        return gameTagsRepository.findAllVisibleTags();
    }

    public Optional<GameTag> getGameTagsById(Long IDGameTag) {
        return gameTagsRepository.findById(IDGameTag);
    }
    public void saveGameTag(GameTag g) {
        gameTagsRepository.save(g);
    }
    public void deleteGameTagById(Long IDGameTag) {
        gameTagsRepository.setVisibleToFalseByIdTag(IDGameTag);
    }
    public Optional<GameTag> gameFormTag(Long IDGameTag) {
        if (IDGameTag!=-1) {
            return gameTagsRepository.findById(IDGameTag);
        } else {
            return Optional.of(new GameTag());
        }
    }
}
