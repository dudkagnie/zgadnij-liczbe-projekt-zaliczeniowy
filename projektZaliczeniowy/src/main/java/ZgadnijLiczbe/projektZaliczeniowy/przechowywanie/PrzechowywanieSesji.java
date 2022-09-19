package ZgadnijLiczbe.projektZaliczeniowy.przechowywanie;

import ZgadnijLiczbe.projektZaliczeniowy.model.SesjaGry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class PrzechowywanieSesji {

    /**
     * przetrzymuje sesje gier
     */
    private ConcurrentHashMap<String, SesjaGry> sesjaGry = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, SesjaGry> getSesjaGry() {
        return this.sesjaGry;
    }

    public boolean zawieraKlucz(String klucz) {
        return this.sesjaGry.containsKey(klucz);
    }

    public void put(String klucz, SesjaGry sesjaGry) {
        this.sesjaGry.put(klucz, sesjaGry);
    }

    public SesjaGry get(String klucz) {
        return this.sesjaGry.get(klucz);
    }

}
