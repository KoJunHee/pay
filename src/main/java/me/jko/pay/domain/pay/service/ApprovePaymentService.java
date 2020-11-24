package me.jko.pay.domain.pay.service;

import lombok.RequiredArgsConstructor;
import me.jko.pay.api.handler.UniqueIdGenerator;
import me.jko.pay.domain.card.service.CardPaymentService;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.domain.card.entity.CardPayment;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.domain.pay.helper.VatCalculator;
import me.jko.pay.domain.pay.repository.ApprovePaymentRepository;
import me.jko.pay.domain.pay.helper.PaymentSecurityManager;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

import static me.jko.pay.domain.pay.enums.PaymentType.PAYMENT;

@Service
@RequiredArgsConstructor
public class ApprovePaymentService {
    private final UniqueIdGenerator uniqueIdGenerator;
    private final PaymentSecurityManager paymentSecurityManager;
    private final VatCalculator vatCalculator;

    private final ApprovePaymentRepository approvePaymentRepository;
    private final CardPaymentService cardPaymentService;

    public CardPayment approve(ApprovePaymentDTO approvePaymentDTO) throws GeneralSecurityException {
        ApprovePayment approvePayment = approvePaymentRepository.save(
            ApprovePayment.builder()
                .id(uniqueIdGenerator.getUniqueId())
                .encryptedCardInfo(paymentSecurityManager.getEncryptedCardInfoFrom(approvePaymentDTO))
                .cardInstallmentMonth(approvePaymentDTO.getCardInstallmentMonth())
                .paymentPrice(approvePaymentDTO.getPaymentPrice())
                .vat(vatCalculator.calculateFrom(approvePaymentDTO))
                .paymentType(PAYMENT)
                .build()
        );

        return cardPaymentService.approve(approvePaymentDTO, approvePayment);
    }
}
