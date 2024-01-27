package com.sportEventReservation.payment.domain;

import com.sportEventReservation.payment.dto.CreatePaymentDto;
import com.sportEventReservation.payment.dto.PaymentDto;
import com.sportEventReservation.payment.exception.PaymentNotFoundException;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PaymentFacade {
  PaymentRepository paymentRepository;

  public PaymentDto makePayment(CreatePaymentDto createPayment) {
    Payment payment = Payment.builder()
        .paymentId(UUID.randomUUID())
        .email(createPayment.getEmail())
        .paymentStatus(PaymentStatus.IN_PROGRESS)
        .reportId(createPayment.getReportId())
        .sportEventId(createPayment.getSportEventId())
        .build();
    return paymentRepository.save(payment).dto();
  }

  public PaymentDto cancelPayment(UUID paymentId) {
    Payment payment = paymentRepository.findPaymentByPaymentId(paymentId)
        .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    payment = payment.toBuilder()
        .paymentStatus(PaymentStatus.CANCELED)
        .build();
    return paymentRepository.save(payment).dto();
  }

  public PaymentDto acceptPayment(UUID paymentId) {
    Payment payment = paymentRepository.findPaymentByPaymentId(paymentId)
        .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    payment = payment.toBuilder()
        .paymentStatus(PaymentStatus.ACCEPTED)
        .build();
    return paymentRepository.save(payment).dto();
  }
}
