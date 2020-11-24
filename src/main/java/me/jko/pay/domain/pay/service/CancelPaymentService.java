package me.jko.pay.domain.pay.service;

import lombok.RequiredArgsConstructor;
import me.jko.pay.api.handler.UniqueIdGenerator;
import me.jko.pay.domain.card.service.CardPaymentService;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.domain.pay.entity.CancelPayment;
import me.jko.pay.api.dto.CardInfo;
import me.jko.pay.domain.card.entity.CardPayment;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.pay.repository.ApprovePaymentRepository;
import me.jko.pay.domain.pay.helper.PaymentSecurityManager;
import me.jko.pay.domain.pay.helper.CancelPriceValidator;
import me.jko.pay.domain.pay.helper.CancelVatValidator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.security.GeneralSecurityException;

import static me.jko.pay.domain.pay.enums.PaymentType.CANCEL;

@Service
@RequiredArgsConstructor
public class CancelPaymentService {
    private final UniqueIdGenerator uniqueIdGenerator;
    private final PaymentSecurityManager paymentSecurityManager;

    private final ApprovePaymentRepository approvePaymentRepository;
    private final CardPaymentService cardPaymentService;

    private final CancelPriceValidator cancelPriceValidator;
    private final CancelVatValidator cancelVatValidator;


    public CardPayment cancel(CancelPaymentDTO cancelPaymentDTO) throws GeneralSecurityException {
        ApprovePayment approvePayment = approvePaymentRepository.findById(cancelPaymentDTO.getId())
            .orElseThrow(EntityNotFoundException::new);

        validate(approvePayment, cancelPaymentDTO);

        approvePayment.cancelPaymentBy(cancelPaymentDTO);
        approvePayment.setPaymentType(CANCEL);

        CardInfo cardInfo = paymentSecurityManager.getCardInfoFrom(approvePayment);

        CancelPayment cancelPayment = CancelPayment.builder()
            .id(uniqueIdGenerator.getUniqueId())
            .encryptedCardInfo(approvePayment.getEncryptedCardInfo())
            .cardInstallmentMonth(approvePayment.getCardInstallmentMonth())
            .paymentPrice(cancelPaymentDTO.getCancelPrice())
            .vat(cancelPaymentDTO.getVat() == null ? approvePayment.getVat() : cancelPaymentDTO.getVat())
            .paymentType(CANCEL)
            .cardInfo(cardInfo)
            .build();

        approvePayment.getCancelPayments().add(cancelPayment);
        approvePaymentRepository.save(approvePayment);

        return cardPaymentService.approve(cancelPaymentDTO, cancelPayment);
    }

    private void validate(ApprovePayment approvePayment, CancelPaymentDTO cancelPaymentDTO) {
        cancelPriceValidator.validate(approvePayment, cancelPaymentDTO);
        cancelVatValidator.validate(approvePayment, cancelPaymentDTO);
    }
}
