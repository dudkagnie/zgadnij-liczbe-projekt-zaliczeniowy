package ZgadnijLiczbe.projektZaliczeniowy.zapytanie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * klasa zawiera potrzebne pola do wygenerowania żądania (id sesji + liczbę
 * wytypowaną przez gracza)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zgadniecie {
    /**
     * id sesji
     */
    private String idSesji;
    /**
     * wytypowana przez gracza liczba
     */
    private int typowanaLiczba;

}
