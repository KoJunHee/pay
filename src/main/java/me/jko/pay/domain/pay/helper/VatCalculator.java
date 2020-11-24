package me.jko.pay.domain.pay.helper;

import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.domain.pay.exception.VatIsGreaterThanPaymentPrice;
import org.springframework.stereotype.Component;

@Component
public class VatCalculator {
    private static final String EXCEPTION_MESSAGE = "Vat Can't be Greater Than Payment Price";
    private static final Long DIVISION = 11L;

    public Long calculateFrom(ApprovePaymentDTO approvePaymentDTO) {
        Double paymentPrice = Double.valueOf(approvePaymentDTO.getPaymentPrice());
        Long vat = approvePaymentDTO.getVat() == null ? Math.round(paymentPrice / DIVISION) : approvePaymentDTO.getVat();

        if (vat > paymentPrice) {
            throw new VatIsGreaterThanPaymentPrice(EXCEPTION_MESSAGE);
        }

        return vat;
    }
}
