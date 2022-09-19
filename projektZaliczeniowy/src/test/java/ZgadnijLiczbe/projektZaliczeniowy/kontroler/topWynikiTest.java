package ZgadnijLiczbe.projektZaliczeniowy.kontroler;

import ZgadnijLiczbe.projektZaliczeniowy.dto.Top10wynikiDto;
import ZgadnijLiczbe.projektZaliczeniowy.serwis.impl.Serwis;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class topWynikiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Serwis serwis;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void jesliWynikiZwroc200() throws Exception {
        Top10wynikiDto dto1 = Top10wynikiDto.builder().idSesji("sesja1").czasTrwania(1000).proby(2)
                .build();
        Top10wynikiDto dto2 = Top10wynikiDto.builder().idSesji("sesja2").czasTrwania(2000).proby(3)
                .build();

        List<Top10wynikiDto> wyniki = List.of(dto1, dto2);
        when(serwis.wyniki()).thenReturn(
                wyniki);

        MvcResult zwrocDane = mockMvc.perform(MockMvcRequestBuilders.get("/hiscores"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String zawartosc = zwrocDane.getResponse().getContentAsString();
        TypeReference<List<Top10wynikiDto>> typeReference = new TypeReference<>() {
        };
        List<Top10wynikiDto> list = objectMapper.readValue(zawartosc, typeReference);

        assertEquals(list.size(), 2);
    }
}
