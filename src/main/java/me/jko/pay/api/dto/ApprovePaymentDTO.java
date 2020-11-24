package me.jko.pay.api.dto;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
public class ApprovePaymentDTO {

    @Valid
    private CardInfo cardInfo;

    @NotNull
    @Min(value = 0)
    @Max(value = 12)
    @Column
    private Integer cardInstallmentMonth;

    @NotNull
    @Min(value = 100)
    @Max(value = 1000000000)
    @Column
    private Long paymentPrice;

    @Column
    @Min(value = 0)
    @Max(value = 1000000000)
    private Long vat;
}
