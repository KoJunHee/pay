package me.jko.pay.domain.pay.helper;

import org.springframework.stereotype.Component;

@Component
public class CardNumberMasker {
    private static final Character maskCharacter = '*';

    public String mask(String cardNumber) {
        StringBuilder stringBuilder = new StringBuilder(cardNumber);

        for (int i = 6; i < stringBuilder.length() - 3; i++) {
            stringBuilder.setCharAt(i, maskCharacter);
        }

        return stringBuilder.toString();
    }
}
