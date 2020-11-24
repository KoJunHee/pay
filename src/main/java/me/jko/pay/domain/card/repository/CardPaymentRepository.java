package me.jko.pay.domain.card.repository;

import me.jko.pay.domain.card.entity.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPaymentRepository extends JpaRepository<CardPayment, String> {
}
