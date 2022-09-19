package ZgadnijLiczbe.projektZaliczeniowy.kontroler;

import ZgadnijLiczbe.projektZaliczeniowy.dto.NowaSesjaDto;
import ZgadnijLiczbe.projektZaliczeniowy.dto.ZgadnijOdpowiedzDto;
import ZgadnijLiczbe.projektZaliczeniowy.zapytanie.Zgadniecie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class kontrolerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void rozpoczecieNowejSesjiZwrocenieId() throws Exception {
        MvcResult zwrocDane = mockMvc.perform(MockMvcRequestBuilders.get("/start"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String zawartosc = zwrocDane.getResponse().getContentAsString();
        NowaSesjaDto dto = objectMapper.readValue(zawartosc, NowaSesjaDto.class);

        assertDoesNotThrow(() -> UUID.fromString(dto.getIdSesji()));
    }

    @Test
    void jesliIdSesjiNiepoprawneZwroc401() throws Exception {
        Zgadniecie zgadnijDto = Zgadniecie.builder().idSesji("aga").typowanaLiczba(1).build();
        String zawartosc = objectMapper.writeValueAsString(zgadnijDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/guess")
                        .contentType("application/json").content(zawartosc))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void jesliPoprawneIdZwroc200() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        NowaSesjaDto dto = restTemplate.exchange("http://localhost:8080/start",
                HttpMethod.GET, null, NowaSesjaDto.class).getBody();

        Zgadniecie zgadniecieDto = Zgadniecie.builder().idSesji(dto.getIdSesji()).typowanaLiczba(50).build();
        String zawartosc = objectMapper.writeValueAsString(zgadniecieDto);

        MvcResult zwrocDane = mockMvc.perform(MockMvcRequestBuilders.post("/guess")
                        .contentType("application/json").content(zawartosc))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ZgadnijOdpowiedzDto odpowiedzDto = objectMapper.readValue(zwrocDane.getResponse().getContentAsString(),
                ZgadnijOdpowiedzDto.class);

        List<String> mozliweWyniki = List.of("winner", "too small", "too big");
        String wynik = odpowiedzDto.getRezultat();

        assertTrue(mozliweWyniki.contains(wynik));
    }
}
