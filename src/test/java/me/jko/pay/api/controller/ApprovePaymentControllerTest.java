package me.jko.pay.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.api.dto.CardInfo;
import me.jko.pay.domain.card.entity.CardPayment;
import me.jko.pay.domain.card.repository.CardPaymentRepository;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.domain.pay.helper.PaymentSecurityManager;
import me.jko.pay.domain.pay.repository.ApprovePaymentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static me.jko.pay.domain.pay.enums.PaymentType.PAYMENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ApprovePaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentSecurityManager paymentSecurityManager;
    @Autowired
    private ApprovePaymentRepository approvePaymentRepository;
    @Autowired
    private CardPaymentRepository cardPaymentRepository;

    @Test
    public void givenApprovePaymentAndCancelPaymentDTO_whenRequestToController_thenSaveApprovePayment() throws Exception {
        // Given
        ApprovePaymentDTO approvePaymentDTO = getApprovePaymentDTO();

        // When
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        // Then
        ApprovePayment approvePayment = approvePaymentRepository.findAll().get(0);

        assertEquals(approvePaymentDTO.getCardInstallmentMonth(), approvePayment.getCardInstallmentMonth());
        assertEquals(approvePaymentDTO.getPaymentPrice(), approvePayment.getPaymentPrice());
        assertEquals(approvePaymentDTO.getVat(), approvePayment.getVat());
        assertEquals(PAYMENT, approvePayment.getPaymentType());
        assertNull(approvePayment.getCardNumber());
        assertNull(approvePayment.getCardExpireDate());
        assertNull(approvePayment.getCardCvc());
    }

    @Test
    public void givenApprovePaymentDTO_whenRequestToController_thenSaveCardPayment() throws Exception {
        // Given
        ApprovePaymentDTO approvePaymentDTO = getApprovePaymentDTO();

        // When
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        // Then
        CardPayment cardPayment = cardPaymentRepository.findAll().get(0);
        assertEquals(450, cardPayment.getResult().length());
    }

    private ApprovePaymentDTO getApprovePaymentDTO() {
        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(getCardInfo());
        approvePaymentDTO.setCardInstallmentMonth(12);
        approvePaymentDTO.setPaymentPrice(20000L);
        approvePaymentDTO.setVat(100L);

        return approvePaymentDTO;
    }

    private CardInfo getCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardCvc("124");
        cardInfo.setCardExpireDate("1212");
        cardInfo.setCardNumber("1234567890");

        return cardInfo;
    }
}
