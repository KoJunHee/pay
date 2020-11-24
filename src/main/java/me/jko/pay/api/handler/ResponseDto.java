package me.jko.pay.api.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseDto {
    private String exception;
    private String message;
}
