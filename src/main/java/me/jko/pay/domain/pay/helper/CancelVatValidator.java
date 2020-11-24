package me.jko.pay.domain.pay.helper;

import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.pay.exception.CancelPaymentVatIsGreaterThanPaymentVat;
import org.springframework.stereotype.Component;

@Component
public class CancelVatValidator {
    private static final String EXCEPTION_MESSAGE = "Cancel Payment Vat Can't be Greater Than Payment Vat";

    public void validate(ApprovePayment approvePayment, CancelPaymentDTO cancelPaymentDTO) {
        Long approvePaymentVat = approvePayment.getVat();
        Long cancelPaymentVat = cancelPaymentDTO.getVat();

        if (cancelPaymentVat != null && cancelPaymentVat > approvePaymentVat) {
            throw new CancelPaymentVatIsGreaterThanPaymentVat(EXCEPTION_MESSAGE);
        }
    }
}
