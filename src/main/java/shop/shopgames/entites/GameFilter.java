package shop.shopgames.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameFilter {
    private String title;
    private Float priceMin;
    private Float priceMax;
    private LocalDate dataMin;
    private LocalDate dataMax;
    private GamePlatform gamePlatform;
    private GameCategory gameCategory;
    private String GameTag;
    private Boolean tagF;
    private Boolean more;

    @Override
    public String toString() {
        return "GameFilter{" +
                "title='" + title + '\'' +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", dataMin=" + dataMin +
                ", dataMax=" + dataMax +
                ", gamePlatform=" + gamePlatform +
                ", gameCategory=" + gameCategory +
                ", GameTag='" + GameTag + '\'' +
                ", tagF=" + tagF +
                '}';
    }
}
