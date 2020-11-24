package me.jko.pay.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SearchPaymentDTO {

    @NotNull
    private String id;
}
