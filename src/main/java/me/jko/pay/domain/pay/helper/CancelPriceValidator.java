package me.jko.pay.domain.pay.helper;

import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.pay.exception.CancelPriceIsGreaterThanPaymentPrice;
import org.springframework.stereotype.Component;

@Component
public class CancelPriceValidator {
    private static final String EXCEPTION_MESSAGE = "Cancel Price Can't be Greater Than Payment Price";

    public void validate(ApprovePayment approvePayment, CancelPaymentDTO cancelPaymentDTO) {
        Long paymentPrice = approvePayment.getPaymentPrice();
        Long cancelPrice = cancelPaymentDTO.getCancelPrice();

        if (cancelPrice > paymentPrice) {
            throw new CancelPriceIsGreaterThanPaymentPrice(EXCEPTION_MESSAGE);
        }
    }
}
