package me.jko.pay.domain.pay.repository;

import me.jko.pay.domain.pay.entity.ApprovePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovePaymentRepository extends JpaRepository<ApprovePayment, String> {
}
