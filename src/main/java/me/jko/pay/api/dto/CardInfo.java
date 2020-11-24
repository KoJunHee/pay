package me.jko.pay.api.dto;

import lombok.*;
import me.jko.pay.domain.pay.annotation.ValidPeriod;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardInfo {
    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 10, max = 16)
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 4, max = 4)
    @ValidPeriod
    private String cardExpireDate;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 3, max = 3)
    private String cardCvc;
}
