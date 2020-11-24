package me.jko.pay.domain.pay.exception;

public class CancelPriceIsGreaterThanPaymentPrice extends RuntimeException {

    public CancelPriceIsGreaterThanPaymentPrice(String message) {
        super(message);
    }
}
