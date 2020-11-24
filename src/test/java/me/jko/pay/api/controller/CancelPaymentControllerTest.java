package me.jko.pay.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.api.dto.CardInfo;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.domain.pay.helper.PaymentSecurityManager;
import me.jko.pay.domain.pay.repository.ApprovePaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static me.jko.pay.domain.pay.enums.PaymentType.CANCEL;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CancelPaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ApprovePaymentRepository approvePaymentRepository;
    @Autowired
    private PaymentSecurityManager paymentSecurityManager;

    private ApprovePayment approvePayment;

    @Before
    public void approvePayment() throws Exception {
        // Given
        CardInfo cardInfo = getCardInfo();
        ApprovePaymentDTO approvePaymentDTO = getApprovePaymentDTO(cardInfo);

        // When, Then
        mockMvc.perform(post("/payments/v1")
            .content(objectMapper.writeValueAsString(approvePaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        approvePayment = approvePaymentRepository.findAll().get(0);
    }


    @Test
    public void givenCancelPaymentDTO_whenRequestToController_thenSaveCancelPayment() throws Exception {
        // Given
        CancelPaymentDTO cancelPaymentDTO = new CancelPaymentDTO();
        cancelPaymentDTO.setId(approvePayment.getId());
        cancelPaymentDTO.setCancelPrice(1000L);
        cancelPaymentDTO.setVat(100L);

        // When
        mockMvc.perform(put("/payments/v1")
            .content(objectMapper.writeValueAsString(cancelPaymentDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        // Then
        ApprovePayment approvePayment = approvePaymentRepository.findById(this.approvePayment.getId()).orElseThrow(EntityNotFoundException::new);

        assertEquals(getApprovePaymentDTO(getCardInfo()).getCardInstallmentMonth(), approvePayment.getCardInstallmentMonth());
        assertEquals(Optional.of(19000L).get(), approvePayment.getPaymentPrice());
        assertEquals(Optional.of(0L).get(), approvePayment.getVat());
        assertEquals(CANCEL, approvePayment.getPaymentType());
        assertNull(approvePayment.getCardNumber());
        assertNull(approvePayment.getCardExpireDate());
        assertNull(approvePayment.getCardCvc());
    }

    private CardInfo getCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardCvc("123");
        cardInfo.setCardExpireDate("1212");
        cardInfo.setCardNumber("1234567890");

        return cardInfo;
    }

    private ApprovePaymentDTO getApprovePaymentDTO(CardInfo cardInfo) {
        ApprovePaymentDTO approvePaymentDTO = new ApprovePaymentDTO();
        approvePaymentDTO.setCardInfo(cardInfo);
        approvePaymentDTO.setCardInstallmentMonth(12);
        approvePaymentDTO.setPaymentPrice(20000L);
        approvePaymentDTO.setVat(100L);

        return approvePaymentDTO;
    }
}
