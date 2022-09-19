package ZgadnijLiczbe.projektZaliczeniowy.kontroler;

import ZgadnijLiczbe.projektZaliczeniowy.dto.NowaSesjaDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.Top10wynikiDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.ZgadnijOdpowiedzDto;
import ZgadnijLiczbe.projektZaliczeniowy.serwis.ISerwis;
import ZgadnijLiczbe.projektZaliczeniowy.wyjatki.SesjaNieIstniejeWyjatek;
import ZgadnijLiczbe.projektZaliczeniowy.wyjatki.SesjaZakonczonaWyjatek;
import ZgadnijLiczbe.projektZaliczeniowy.zapytanie.Zgadniecie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Kontroler REST api zajmujący się obsługą żądań dotyczących gry
 */
@RestController
public class Kontroler {

    /**
     * Przy pomocy mechanizmu wstrzykiwania zależności uzyskujemy dostęp do
     * instancji serwisu
     */
    @Autowired
    private ISerwis serwis;

    /**
     * Endpoint start
     *
     * @return zwraca id sesji
     */
    @GetMapping("/start")

    public ResponseEntity<NowaSesjaDto> start() {
        NowaSesjaDto sesja = serwis.nowaSesja();

        return new ResponseEntity<>(sesja, HttpStatus.OK);
    }

    /**
     * Endpoint guess
     * @return zwraca id sesji, liczbę prób oraz rezultat
     */
    @PostMapping("/guess")
    public ZgadnijOdpowiedzDto zgadnij(@RequestBody Zgadniecie dto)
            throws SesjaNieIstniejeWyjatek, SesjaZakonczonaWyjatek
    {
        ZgadnijOdpowiedzDto odpowiedz =  serwis.zgadniecie(dto);

        return odpowiedz;

    }

    /**
     * Endpoint hiscores
     * @return zwraca id sesji, liczbę prób oraz czas trwania sesji
     */
    @GetMapping(path = "hiscores")
    public ResponseEntity<List<Top10wynikiDto>> wyniki() {
        List<Top10wynikiDto> topWyniki = serwis.wyniki();

        return new ResponseEntity<>(topWyniki, HttpStatus.OK);
    }

}