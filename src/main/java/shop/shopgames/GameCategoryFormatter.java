package shop.shopgames;

import org.springframework.format.Formatter;
import shop.shopgames.entites.GameCategory;

import java.text.ParseException;
import java.util.Locale;

public class GameCategoryFormatter implements Formatter<GameCategory> {

    @Override
    public GameCategory parse(String text, Locale locale) throws ParseException {
        if (text == null || text.isEmpty()) {
            return null;
        }
        String tab[] = text.split("-");
        Long IdCategory = Long.parseLong(tab[0]);
        Boolean Visible = Boolean.parseBoolean(tab[1]);
        String Name = tab[2];
        GameCategory gameCategory = new GameCategory();
        gameCategory.setIdCategory(IdCategory);
        gameCategory.setVisible(Visible);
        gameCategory.setName(Name);
        return gameCategory;
    }

    @Override
    public String print(GameCategory object, Locale locale) {
        if (object == null) {
            return "";
        }
        return object.getIdCategory()+"-"+object.isVisible()+"-"+object.getName();
    }
}
