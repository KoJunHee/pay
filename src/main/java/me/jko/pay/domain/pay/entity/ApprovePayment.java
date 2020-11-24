package me.jko.pay.domain.pay.entity;

import lombok.*;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.pay.enums.PaymentType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovePayment {
    @Id
    @Column(length = 20)
    private String id;

    @Column(length = 20)
    private String paymentId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId")
    private List<CancelPayment> cancelPayments;

    @Column
    private String encryptedCardInfo;

    @Column
    private Integer cardInstallmentMonth;

    @Column
    private Long paymentPrice;

    @Column
    private Long vat;

    @Column
    private PaymentType paymentType;

    @Transient
    private String cardNumber;

    @Transient
    private String cardExpireDate;

    @Transient
    private String cardCvc;

    public void cancelPaymentBy(CancelPaymentDTO cancelPaymentDTO) {
        Long paymentPrice = getPaymentPrice();
        Long paymentVat = getVat();

        Long cancelPrice = cancelPaymentDTO.getCancelPrice();
        Long cancelVat = cancelPaymentDTO.getVat();

        setPaymentPrice(paymentPrice - cancelPrice);
        setVat(cancelVat == null ? Math.round(Double.valueOf(paymentPrice) / 11L) : paymentVat - cancelVat);
    }
}
