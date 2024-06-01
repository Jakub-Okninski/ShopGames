package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.shopgames.entites.GamePlatform;
import shop.shopgames.entites.GameTag;
import shop.shopgames.repositories.GamePlatformRepository;
import shop.shopgames.repositories.GameTagsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GamePlatformService {
    private GamePlatformRepository gamePlatformRepository;

    @Autowired
    private GamePlatformService(GamePlatformRepository gamePlatformRepository) {
        this.gamePlatformRepository = gamePlatformRepository;
    }
    public List<GamePlatform> getGamesPlatforms() {
        return gamePlatformRepository.findAllVisiblePlatforms();
    }

    public Optional<GamePlatform> getGamePlatformById(Long IDGamePlatform) {
        return gamePlatformRepository.findById(IDGamePlatform);
    }
    public void saveGamePlatfrom(GamePlatform g) {
        gamePlatformRepository.save(g);
    }
    public void deleteGamePlatformById(Long IDGamePlatform) {
        gamePlatformRepository.setVisibleToFalseByIdPlatfrom(IDGamePlatform);
    }
    public Optional<GamePlatform> gameFormPlatfrom(Long IDGameplatfrom) {
        if (IDGameplatfrom!=-1) {
            return gamePlatformRepository.findById(IDGameplatfrom);
        } else {
            return Optional.of(new GamePlatform());
        }
    }
}
