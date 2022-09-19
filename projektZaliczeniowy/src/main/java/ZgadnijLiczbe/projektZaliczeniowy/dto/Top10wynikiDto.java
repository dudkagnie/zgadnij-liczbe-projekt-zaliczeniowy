package ZgadnijLiczbe.projektZaliczeniowy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * klasa zawiera potrzebne pola do wysłania odpowiedzi na żadanie podania 10
 * najlepszych wyników:
 * id sesji
 * liczbę prób
 * czas trwania
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Top10wynikiDto {
    /**
     * id sesji
     */
    private String idSesji;

    /**
     * liczba prób
     */
    private int proby;

    /**
     * czas trwania sesji
     */
    private long czasTrwania;

}
