package me.jko.pay.domain.pay.service;

import lombok.RequiredArgsConstructor;
import me.jko.pay.domain.pay.entity.ApprovePayment;
import me.jko.pay.api.dto.SearchPaymentDTO;
import me.jko.pay.domain.pay.repository.ApprovePaymentRepository;
import me.jko.pay.domain.pay.helper.PaymentSecurityManager;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class SearchPaymentService {
    private final PaymentSecurityManager paymentSecurityManager;
    private final ApprovePaymentRepository approvePaymentRepository;

    public ApprovePayment search(SearchPaymentDTO searchPaymentDTO) throws GeneralSecurityException {
        ApprovePayment approvePayment = approvePaymentRepository.findById(searchPaymentDTO.getId())
            .orElseThrow(EntityNotFoundException::new);

        return paymentSecurityManager.getPaymentForResponseFrom(approvePayment);
    }
}
