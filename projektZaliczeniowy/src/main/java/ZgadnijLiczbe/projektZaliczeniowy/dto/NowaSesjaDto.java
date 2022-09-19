package ZgadnijLiczbe.projektZaliczeniowy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * klasa potrzebna do wygerenowania id sesji
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NowaSesjaDto {
    /**
     * id sesji
     */
    private String idSesji;
}
