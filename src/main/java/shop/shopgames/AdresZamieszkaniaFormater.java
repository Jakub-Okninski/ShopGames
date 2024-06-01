package shop.shopgames;

import org.springframework.format.Formatter;
import shop.shopgames.entites.AdresZamieszkania;
import shop.shopgames.entites.GameCategory;

import java.text.ParseException;
import java.util.Locale;

public class AdresZamieszkaniaFormater implements Formatter<AdresZamieszkania> {

    @Override
    public AdresZamieszkania parse(String text, Locale locale) throws ParseException {
        System.out.println("Formatowanie adresu");
        System.out.println(text);
        String tab[] = text.split(" ");
        AdresZamieszkania a = new AdresZamieszkania();
        if(tab.length==2){
            System.out.println("2");
            a.setStreet(tab[0]);
            a.setHouseNumber(tab[1]);
        }
        if(tab.length==3){
            System.out.println("3");
            a.setStreet(tab[0]+" "+tab[1]);
            a.setHouseNumber(tab[2]);
        }

        return a;
    }

    @Override
    public String print(AdresZamieszkania object, Locale locale) {
        return object.getStreet()+" "+object.getHouseNumber();
    }









}
