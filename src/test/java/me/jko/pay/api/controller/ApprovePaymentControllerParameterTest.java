package me.jko.pay.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.api.dto.CardInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ApprovePaymentControllerParameterTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenWrongCardCvc_whenRequestToController_thenThrowsMethodArgumentNotValidException() throws Exception {
        // Given
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardCvc("1234");
        cardInfo.setCardExpireDate("1212");
        cardInfo.setCardNumber("1234567890");

        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(cardInfo);
        approvePaymentDTO.setCardInstallmentMonth(12);
        approvePaymentDTO.setPaymentPrice(20000L);
        approvePaymentDTO.setVat(100L);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(MethodArgumentNotValidException.class, result.getResolvedException().getClass()))
            .andReturn();
    }

    @Test
    public void givenWrongCardExpireDate_whenRequestToController_thenThrowsMethodArgumentNotValidException() throws Exception {
        // Given
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardCvc("1234");
        cardInfo.setCardExpireDate("1312");
        cardInfo.setCardNumber("1234567890");

        ApprovePaymentDTO approvePaymentDTO = getPaymentDTO(cardInfo);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(MethodArgumentNotValidException.class, result.getResolvedException().getClass()))
            .andReturn();
    }

    @Test
    public void givenWrongCardNumber_whenRequestToController_thenThrowsMethodArgumentNotValidException() throws Exception {
        // Given
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardCvc("123");
        cardInfo.setCardExpireDate("0912");
        cardInfo.setCardNumber("123456789");

        ApprovePaymentDTO approvePaymentDTO = getPaymentDTO(cardInfo);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(MethodArgumentNotValidException.class, result.getResolvedException().getClass()))
            .andReturn();
    }

    @Test
    public void givenWrongCardInstallmentMonth_whenRequestToController_thenThrowsMethodArgumentNotValidException() throws Exception {
        // Given
        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(getCardInfo());
        approvePaymentDTO.setCardInstallmentMonth(13);
        approvePaymentDTO.setPaymentPrice(20000L);
        approvePaymentDTO.setVat(100L);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(MethodArgumentNotValidException.class, result.getResolvedException().getClass()))
            .andReturn();
    }

    @Test
    public void givenWrongPaymentPrice_whenRequestToController_thenThrowsMethodArgumentNotValidException() throws Exception {
        // Given
        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(getCardInfo());
        approvePaymentDTO.setCardInstallmentMonth(13);
        approvePaymentDTO.setPaymentPrice(-1L);
        approvePaymentDTO.setVat(100L);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(MethodArgumentNotValidException.class, result.getResolvedException().getClass()))
            .andReturn();
    }

    @Test
    public void givenWrongVat_whenRequestToController_thenThrowsMethodArgumentNotValidException() throws Exception {
        // Given
        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(getCardInfo());
        approvePaymentDTO.setCardInstallmentMonth(13);
        approvePaymentDTO.setPaymentPrice(10000L);
        approvePaymentDTO.setVat(-1L);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(result -> assertEquals(MethodArgumentNotValidException.class, result.getResolvedException().getClass()))
            .andReturn();
    }

    private CardInfo getCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardCvc("1234");
        cardInfo.setCardExpireDate("1312");
        cardInfo.setCardNumber("1234567890");

        return cardInfo;
    }

    private ApprovePaymentDTO getPaymentDTO(CardInfo cardInfo) {
        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(cardInfo == null ? getCardInfo() : cardInfo);
        approvePaymentDTO.setCardInstallmentMonth(12);
        approvePaymentDTO.setPaymentPrice(20000L);
        approvePaymentDTO.setVat(100L);

        return approvePaymentDTO;
    }
}
