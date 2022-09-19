package ZgadnijLiczbe.projektZaliczeniowy.wyjatki;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek rzucany przez aplikację, gdy gracz kontynuuje odgadywanie liczby, chociaż podał już poprawną liczbę.
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Ta gra została zakończona, zacznij nową grę.")
public class SesjaZakonczonaWyjatek extends Exception {

}

