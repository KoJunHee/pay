package me.jko.pay.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jko.pay.api.dto.SearchPaymentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SearchPaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenSearchPaymentDTOWithNullId_whenRequestToController_thenThrowsInvalidDataAccessApiUsageException() throws Exception {
        // Given
        SearchPaymentDTO searchPaymentDTO = new SearchPaymentDTO();
        searchPaymentDTO.setId(null);

        // When, Then
        mockMvc.perform(get("/payments/v1")
            .content(objectMapper.writeValueAsString(searchPaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(InvalidDataAccessApiUsageException.class, result.getResolvedException().getClass()))
            .andReturn();
    }
}
