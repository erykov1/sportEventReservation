package com.sportEventReservation.payment.domain;

import com.sportEventReservation.payment.dto.PaymentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "payments")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Payment {
  @Id
  UUID paymentId;
  String email;
  PaymentStatus paymentStatus;
  UUID reportId;
  UUID sportEventId;

  PaymentDto dto() {
    return PaymentDto.builder()
        .paymentId(paymentId)
        .email(email)
        .paymentStatus(paymentStatus.dto())
        .reportId(reportId)
        .sportEventId(sportEventId)
        .build();
  }
}
