package me.jko.pay.domain.pay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.jko.pay.domain.pay.annotation.CardPaymentDataField;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.pay.enums.PaymentType;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

import static me.jko.pay.domain.pay.enums.CardPaymentDataFieldType.*;

@Getter
@Setter
public class CardPaymentData {
    @CardPaymentDataField(cardPaymentDataFieldType = CHARACTER, length = 10)
    private PaymentType type;

    @CardPaymentDataField(cardPaymentDataFieldType = CHARACTER, length = 20)
    private String id;

    @CardPaymentDataField(cardPaymentDataFieldType = NUMBER_LEFT, length = 20)
    private String cardNumber;

    @CardPaymentDataField(cardPaymentDataFieldType = NUMBER_ZERO, length = 2)
    private Integer cardInstallmentMonth;

    @CardPaymentDataField(cardPaymentDataFieldType = NUMBER_LEFT, length = 4)
    private String cardExpiryDate;

    @CardPaymentDataField(cardPaymentDataFieldType = NUMBER_LEFT, length = 3)
    private String cardCvc;

    @CardPaymentDataField(cardPaymentDataFieldType = NUMBER, length = 10)
    private Long paymentPrice;

    @CardPaymentDataField(cardPaymentDataFieldType = NUMBER_ZERO, length = 10)
    private Long vat;

    @CardPaymentDataField(cardPaymentDataFieldType = CHARACTER, length = 20)
    private String paymentId;

    @CardPaymentDataField(cardPaymentDataFieldType = CHARACTER, length = 300)
    private String cardInfo;

    @CardPaymentDataField(cardPaymentDataFieldType = CHARACTER, length = 47)
    private String reserved;

    public CardPaymentData(ApprovePaymentDTO approvePaymentDTO, ApprovePayment approvePayment) {
        this.type = approvePayment.getPaymentType();
        this.id = approvePayment.getId();

        this.cardInstallmentMonth = approvePaymentDTO.getCardInstallmentMonth();
        this.paymentPrice = approvePaymentDTO.getPaymentPrice();
        this.vat = approvePayment.getVat();

        this.cardNumber = approvePaymentDTO.getCardInfo().getCardNumber();
        this.cardExpiryDate = approvePaymentDTO.getCardInfo().getCardExpireDate();
        this.cardCvc = approvePaymentDTO.getCardInfo().getCardCvc();
        this.cardInfo = approvePayment.getEncryptedCardInfo();

        this.paymentId = "";
        this.reserved = "";
    }

    public CardPaymentData(CancelPaymentDTO cancelPaymentDTO, CancelPayment cancelPayment) {
        this.type = cancelPayment.getPaymentType();
        this.id = cancelPayment.getId();

        this.cardInstallmentMonth = cancelPayment.getCardInstallmentMonth();
        this.paymentPrice = cancelPayment.getPaymentPrice();
        this.vat = cancelPayment.getVat();

        this.cardNumber = cancelPayment.getCardInfo().getCardNumber();
        this.cardExpiryDate = cancelPayment.getCardInfo().getCardExpireDate();
        this.cardCvc = cancelPayment.getCardInfo().getCardCvc();
        this.cardInfo = cancelPayment.getEncryptedCardInfo();

        this.paymentId = cancelPaymentDTO.getId();
        this.reserved = "";
    }

    @SneakyThrows
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Field field : FieldUtils.getAllFields(this.getClass())) {
            CardPaymentDataField annotation = field.getAnnotation(CardPaymentDataField.class);
            String fieldValue = FieldUtils.readField(field, this, true).toString();

            switch (annotation.cardPaymentDataFieldType()) {
                case NUMBER:
                    stringBuilder.append(
                        leftPadWithBlank(fieldValue, annotation.length())
                    );
                    break;
                case NUMBER_ZERO:
                    stringBuilder.append(
                        leftPadWithZero(fieldValue, annotation.length())
                    );
                    break;
                case NUMBER_LEFT:
                case CHARACTER:
                    stringBuilder.append(
                        rightPadWithBlank(fieldValue, annotation.length())
                    );
                    break;
            }
        }

        return getAppendedTotalSizeTo(stringBuilder.toString());
    }

    private String getAppendedTotalSizeTo(String toString) {
        return leftPadWithBlank(String.valueOf(toString.length()), 4) + toString;
    }

    private String leftPadWithBlank(String text, int length) {
        return String.format("%" + length + "." + length + "s", text).replace(' ', '_');
    }

    private String leftPadWithZero(String text, int length) {
        return String.format("%" + length + "." + length + "s", text).replace(' ', '0');
    }

    private String rightPadWithBlank(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text).replace(' ', '_');
    }
}
