package ZgadnijLiczbe.projektZaliczeniowy.serwis.impl;

import ZgadnijLiczbe.projektZaliczeniowy.dto.NowaSesjaDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.Top10wynikiDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.ZgadnijOdpowiedzDto;
import ZgadnijLiczbe.projektZaliczeniowy.model.SesjaGry;
import ZgadnijLiczbe.projektZaliczeniowy.przechowywanie.PrzechowywanieSesji;
import ZgadnijLiczbe.projektZaliczeniowy.serwis.ISerwis;
import ZgadnijLiczbe.projektZaliczeniowy.wyjatki.SesjaNieIstniejeWyjatek;
import ZgadnijLiczbe.projektZaliczeniowy.wyjatki.SesjaZakonczonaWyjatek;
import ZgadnijLiczbe.projektZaliczeniowy.zapytanie.Zgadniecie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Serwis implements ISerwis {

    @Autowired
    private PrzechowywanieSesji przechowywanieSesji;

    /**
     * Wyznacza długość listy najlepszych wyników
     */
    private static int ileWynikow = 10;

    /**
     * przetrzymuje najlepsze wyniki gier
     */
    private List<Top10wynikiDto> wyniki = new LinkedList<>();

    /**
     * początek sesji:
     * generowanie losowej liczby, ktorą gracz będzie musiał zgadnąć
     * tworzy nową sesję gry (id sesji + liczba do odgadnięcia)
     * @return zwraca nowy id sesji
     */
    @Override
    public NowaSesjaDto nowaSesja() {
        UUID uuid;
        String idSesji;

        do {
            uuid = UUID.randomUUID();
            idSesji = uuid.toString();
        } while (przechowywanieSesji.zawieraKlucz(idSesji));

        Random random = new Random();
        int poprawnyNumer = random.nextInt(101);
        SesjaGry sesjaGry = SesjaGry.builder().poprawnyNumer(poprawnyNumer).build();
        przechowywanieSesji.put(idSesji, sesjaGry);

        return NowaSesjaDto.builder().idSesji(idSesji).build();
    }

    /**
     * Metoda, która na podstawie otrzymany od gracza - żetonu oraz liczby - ustala czy:
     * żeton pasuje do jakiekolwiek rozpoczętej sesji gry, sprawdza czy gra się nie zakończyła,
     * a następnie ustala czy liczba podana przez gracza odpowiada wylosowanej liczbie
     *
     * @param dto Zawiera w sobie dane żetonu oraz liczby będącej próbą odgadnięcia przez gracza
     *
     * @return Zawiera w sobie żeton sesji, liczbę prób dotychczas podjętych przez gracza oraz wynik
     * zgadywania: gdy liczba była zbyt mała zwracane jest "too small", gdy jest zbyt duża zwracane jest
     * "too big", gdy użytkownik poprawnie odgadł zwracane jest "winner"
     *
     */
    @Override
    public ZgadnijOdpowiedzDto zgadniecie(Zgadniecie dto)
            throws SesjaNieIstniejeWyjatek, SesjaZakonczonaWyjatek
    {
        String idSesji =  dto.getIdSesji();

        if (!przechowywanieSesji.zawieraKlucz(idSesji)) {
            throw new SesjaNieIstniejeWyjatek();
        }

        SesjaGry sesjaGry = przechowywanieSesji.get(idSesji);

        if (sesjaGry.getKoniec() != null) {
            throw new SesjaZakonczonaWyjatek();
        }

        int liczbaProb = sesjaGry.getLiczbaProb();
        sesjaGry.setLiczbaProb(++liczbaProb);

        int poprawnyNumer = sesjaGry.getPoprawnyNumer();
        int zgadniecie = dto.getTypowanaLiczba();

        ZgadnijOdpowiedzDto rezultat = ZgadnijOdpowiedzDto.builder().idSesji(idSesji).liczbaProb(liczbaProb).build();

        if (zgadniecie < poprawnyNumer) {
            rezultat.setRezultat("too small");
        } else if (zgadniecie > poprawnyNumer) {
            rezultat.setRezultat("too big");
        } else {
            rezultat.setRezultat("winner");
            sesjaGry.setKoniec(new Date());

            long czasTrwania = sesjaGry.getKoniec().getTime() - sesjaGry.getStart().getTime();

            if (wyniki.size() >= ileWynikow) {
                int index = wyniki.size() - 1;
                Top10wynikiDto top10wynikiDto = wyniki.get(index);
                int sLiczbaProb = top10wynikiDto.getProby();
                long sczasTrwania = top10wynikiDto.getCzasTrwania();

                if (sLiczbaProb > liczbaProb || (sLiczbaProb == liczbaProb && sczasTrwania > czasTrwania)) {
                    wyniki.remove(index);
                }
            }

            if (wyniki.size() < ileWynikow) {
                Top10wynikiDto top10wynikiDto = Top10wynikiDto.builder().idSesji(idSesji)
                        .proby(liczbaProb)
                        .czasTrwania(czasTrwania).build();

                wyniki.add(top10wynikiDto);

                wyniki.sort((a, b) -> {
                    int roznica = a.getProby() - b.getProby();
                    long czasRoznica = a.getCzasTrwania() - b.getCzasTrwania();

                    return roznica > 0 ? 1 : (roznica < 0 ? -1 : (czasRoznica > 0 ? 1 : -1));
                });
            }

        }
        return rezultat;
    }

    @Override
    public List<Top10wynikiDto> wyniki() {
        return wyniki;
    }

}

