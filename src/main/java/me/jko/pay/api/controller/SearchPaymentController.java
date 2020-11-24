package me.jko.pay.api.controller;

import lombok.RequiredArgsConstructor;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.api.dto.SearchPaymentDTO;
import me.jko.pay.domain.pay.service.SearchPaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;

@RequestMapping("/payments/v1")
@RestController
@RequiredArgsConstructor
public class SearchPaymentController {
    private final SearchPaymentService paymentService;

    @GetMapping
    public ApprovePayment searchPayment(SearchPaymentDTO searchPaymentDTO) throws GeneralSecurityException {
        System.out.println("324" + searchPaymentDTO.getId());
        return paymentService.search(searchPaymentDTO);
    }
}
