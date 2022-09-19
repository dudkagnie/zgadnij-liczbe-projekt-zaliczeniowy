package ZgadnijLiczbe.projektZaliczeniowy.serwis;

import ZgadnijLiczbe.projektZaliczeniowy.dto.NowaSesjaDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.Top10wynikiDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.ZgadnijOdpowiedzDto;
import ZgadnijLiczbe.projektZaliczeniowy.wyjatki.SesjaNieIstniejeWyjatek;
import ZgadnijLiczbe.projektZaliczeniowy.wyjatki.SesjaZakonczonaWyjatek;
import ZgadnijLiczbe.projektZaliczeniowy.zapytanie.Zgadniecie;

import java.util.List;

public interface ISerwis {

    public NowaSesjaDto nowaSesja();

    public ZgadnijOdpowiedzDto zgadniecie(Zgadniecie dto) throws SesjaNieIstniejeWyjatek, SesjaZakonczonaWyjatek;

    public List<Top10wynikiDto> wyniki();
}
