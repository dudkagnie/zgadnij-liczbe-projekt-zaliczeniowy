package ZgadnijLiczbe.projektZaliczeniowy.wyjatki;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek rzucany przez aplikację, gdy użytkownik prześle błędny żeton uwierzytelniający.
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Podana sesja nie istnieje.")
public class SesjaNieIstniejeWyjatek extends Exception {

}
