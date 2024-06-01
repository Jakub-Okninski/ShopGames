package shop.shopgames;

import org.springframework.format.Formatter;
import shop.shopgames.entites.GameCategory;
import shop.shopgames.entites.GamePlatform;

import java.text.ParseException;
import java.util.Locale;

public class GamePlatformFormatter implements Formatter<GamePlatform> {

    @Override
    public GamePlatform parse(String text, Locale locale) throws ParseException {
        if (text == null || text.isEmpty()) {
            return null;
        }
        System.out.println("formatowanie...");
        String tab[] = text.split("-");
        Long IdCategory = Long.parseLong(tab[0]);
        Boolean Visible = Boolean.parseBoolean(tab[1]);
        String Name = tab[2];
        Boolean Digital = Boolean.parseBoolean(tab[3]);

        GamePlatform gamePlatform = new GamePlatform();
        gamePlatform.setIdPlatform(IdCategory);
        gamePlatform.setVisible(Visible);
        gamePlatform.setName(Name);
        gamePlatform.setDigital(Digital);

        return gamePlatform;
    }

    @Override
    public String print(GamePlatform object, Locale locale) {
        if (object == null) {
            return "";
        }
        return object.getIdPlatform()+"-"+object.isVisible()+"-"+object.getName()+"-"+object.isDigital();
    }
}
