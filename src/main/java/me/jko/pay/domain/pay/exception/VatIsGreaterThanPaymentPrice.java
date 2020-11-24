package me.jko.pay.domain.pay.exception;

public class VatIsGreaterThanPaymentPrice extends RuntimeException {

    public VatIsGreaterThanPaymentPrice(String message) {
        super(message);
    }
}
