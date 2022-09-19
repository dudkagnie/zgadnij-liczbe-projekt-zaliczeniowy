package ZgadnijLiczbe.projektZaliczeniowy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

import java.util.Date;

/**
 * Informacje o sesji
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesjaGry {
    /**
     * poprawny numer, który należy zgadnąć podczas gry
     */
    private int poprawnyNumer;

    /**
     * liczba prób
     */
    @Default
    private int liczbaProb = 0;

    /**
     * data startu sesji
     */
    @Default
    private Date start = new Date();

    /**
     * data końca sesji
     */
    @Default
    private Date koniec = null;

}
