package me.jko.pay.api.controller;

import lombok.RequiredArgsConstructor;
import me.jko.pay.domain.card.entity.CardPayment;
import me.jko.pay.api.dto.ApprovePaymentDTO;
import me.jko.pay.api.dto.ResponseDTO;
import me.jko.pay.domain.pay.service.ApprovePaymentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.GeneralSecurityException;

@RequestMapping("/payments/v1")
@RestController
@RequiredArgsConstructor
public class ApprovePaymentController {
    private final ApprovePaymentService paymentService;

    @PostMapping
    public ResponseDTO approvePayment(@Valid @RequestBody ApprovePaymentDTO approvePaymentDTO) throws GeneralSecurityException {
        CardPayment cardPayment = paymentService.approve(approvePaymentDTO);

        return ResponseDTO.builder()
            .id(cardPayment.getId())
            .result(cardPayment.getResult())
            .build();
    }
}
