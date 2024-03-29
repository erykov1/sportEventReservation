package com.sportEventReservation.payment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface PaymentRepository extends JpaRepository<Payment, UUID> {
  Optional<Payment> findPaymentByPaymentId(UUID paymentId);
}
