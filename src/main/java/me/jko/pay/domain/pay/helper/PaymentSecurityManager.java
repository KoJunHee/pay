package me.jko.pay.domain.pay.helper;

import lombok.RequiredArgsConstructor;
import me.jko.pay.api.dto.CardInfo;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

import static java.util.Arrays.*;

@Component
@RequiredArgsConstructor
public class PaymentSecurityManager {
    private final Encrypter encrypter;
    private final Decrypter decrypter;
    private final CardNumberMasker cardNumberMasker;

    private static final String DELIMITER = "|";

    public String getEncryptedCardInfoFrom(ApprovePaymentDTO approvePaymentDTO) throws GeneralSecurityException {
        return encrypter.encrypt(
            String.join(DELIMITER, asList(approvePaymentDTO.getCardInfo().getCardNumber(), approvePaymentDTO.getCardInfo().getCardExpireDate(), approvePaymentDTO.getCardInfo().getCardCvc()))
        );
    }

    public String getDecryptedCardInfoFrom(String encryptedCardInfo) throws GeneralSecurityException {
        return decrypter.decrypt(encryptedCardInfo);
    }

    public CardInfo getCardInfoFrom(ApprovePayment approvePayment) throws GeneralSecurityException {
        String[] splitedCardInfo = getDecryptedCardInfoFrom(approvePayment.getEncryptedCardInfo())
            .split("\\" + DELIMITER);

        return CardInfo.builder()
            .cardNumber(splitedCardInfo[0])
            .cardExpireDate(splitedCardInfo[1])
            .cardCvc(splitedCardInfo[2])
            .build();
    }

    public ApprovePayment getPaymentForResponseFrom(ApprovePayment approvePayment) throws GeneralSecurityException {
        String[] splitedCardInfo = getDecryptedCardInfoFrom(approvePayment.getEncryptedCardInfo())
            .split("\\" + DELIMITER);

        approvePayment.setEncryptedCardInfo(null);
        approvePayment.setCardNumber(cardNumberMasker.mask(splitedCardInfo[0]));
        approvePayment.setCardExpireDate(splitedCardInfo[1]);
        approvePayment.setCardCvc(splitedCardInfo[2]);

        return approvePayment;
    }
}
