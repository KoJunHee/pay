package me.jko.pay.api.controller;

import lombok.RequiredArgsConstructor;
import me.jko.pay.api.dto.ResponseDTO;
import me.jko.pay.domain.card.entity.CardPayment;
import me.jko.pay.api.dto.CancelPaymentDTO;
import me.jko.pay.domain.pay.service.CancelPaymentService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.GeneralSecurityException;

@RequestMapping("/payments/v1")
@RestController
@RequiredArgsConstructor
public class CancelPaymentController {
    private final CancelPaymentService paymentService;

    @PutMapping
    public ResponseDTO cancelPayment(@Valid @RequestBody CancelPaymentDTO CancelPaymentDTO) throws GeneralSecurityException {
        CardPayment cardPayment = paymentService.cancel(CancelPaymentDTO);

        return ResponseDTO.builder()
            .id(cardPayment.getId())
            .result(cardPayment.getResult())
            .build();
    }
}
