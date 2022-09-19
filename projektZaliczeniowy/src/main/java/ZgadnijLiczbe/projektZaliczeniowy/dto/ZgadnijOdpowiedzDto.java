package ZgadnijLiczbe.projektZaliczeniowy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * klasa zawiera potrzebne pola do wysłania odpowiedzi:
 * id sesji
 * liczbę prób
 * rezultat ("too big", "too small", "winner")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZgadnijOdpowiedzDto {
    /**
     * id sesji
     */
    private String idSesji;

    /**
     * liczba prób
     */
    private int liczbaProb;

    /**
     * rezultat
     */
    private String rezultat;
}
