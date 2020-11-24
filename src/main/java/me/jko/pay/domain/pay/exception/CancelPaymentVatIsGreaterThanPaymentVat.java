package me.jko.pay.domain.pay.exception;

public class CancelPaymentVatIsGreaterThanPaymentVat extends RuntimeException {

    public CancelPaymentVatIsGreaterThanPaymentVat(String message) {
        super(message);
    }
}
