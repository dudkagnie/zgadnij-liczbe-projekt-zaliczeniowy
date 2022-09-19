package ZgadnijLiczbe.projektZaliczeniowy.kontroler;

import ZgadnijLiczbe.projektZaliczeniowy.model.SesjaGry;
import ZgadnijLiczbe.projektZaliczeniowy.przechowywanie.PrzechowywanieSesji;
import ZgadnijLiczbe.projektZaliczeniowy.zapytanie.Zgadniecie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class kontrolerBladTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrzechowywanieSesji przechowywanieSesji;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void jesliGraSkonczonaZwroc422() throws Exception {
        UUID uuid = UUID.randomUUID();
        String idSesji = uuid.toString();

        SesjaGry sesjaGry = SesjaGry.builder().poprawnyNumer(50).koniec(new Date()).build();

        when(przechowywanieSesji.get(idSesji)).thenReturn(sesjaGry);
        when(przechowywanieSesji.zawieraKlucz(idSesji)).thenReturn(true);

        Zgadniecie zgadniecie = Zgadniecie.builder().idSesji(idSesji).typowanaLiczba(50).build();
        String zawartosc = objectMapper.writeValueAsString(zgadniecie);

        mockMvc.perform(MockMvcRequestBuilders.post("/guess")
                        .contentType("application/json").content(zawartosc))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andReturn();
    }
}
