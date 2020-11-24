package me.jko.pay.domain.pay.entity;

import lombok.*;
import me.jko.pay.api.dto.CardInfo;
import me.jko.pay.domain.pay.enums.PaymentType;

import javax.persistence.*;

@Builder
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelPayment {
    @Id
    @Column(length = 20)
    private String id;

    @Column(length = 20)
    private String paymentId;

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
    private CardInfo cardInfo;
}
