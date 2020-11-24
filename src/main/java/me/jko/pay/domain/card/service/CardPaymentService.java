package me.jko.pay.domain.card.service;

import lombok.RequiredArgsConstructor;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.domain.pay.entity.CancelPayment;
import me.jko.pay.domain.pay.entity.CardPaymentData;
import me.jko.pay.domain.card.entity.CardPayment;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.card.repository.CardPaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardPaymentService {
    private final CardPaymentRepository cardPaymentRepository;

    public CardPayment approve(ApprovePaymentDTO approvePaymentDTO, ApprovePayment approvePayment) {
        return cardPaymentRepository.save(
            CardPayment.builder()
                .id(approvePayment.getId())
                .result(new CardPaymentData(approvePaymentDTO, approvePayment).toString())
                .build()
        );
    }

    public CardPayment approve(CancelPaymentDTO cancelPaymentDTO, CancelPayment cancelPayment) {
        return cardPaymentRepository.save(
            CardPayment.builder()
                .id(cancelPayment.getId())
                .result(new CardPaymentData(cancelPaymentDTO, cancelPayment).toString())
                .build()
        );
    }
}
