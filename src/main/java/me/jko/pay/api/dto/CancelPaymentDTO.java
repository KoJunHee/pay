package me.jko.pay.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
public class CancelPaymentDTO {

    @NotNull
    @Size(min = 20, max = 20)
    private String id;

    @NotNull
    @Min(value = 100)
    @Max(value = 1000000000)
    private Long cancelPrice;

    @Column
    @Min(value = 0)
    @Max(value = 1000000000)
    private Long vat;
}
