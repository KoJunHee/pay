package me.jko.pay.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String id;
    private String result;
}
