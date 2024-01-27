package com.sportEventReservation.payment.domain;

import com.sportEventReservation.payment.dto.PaymentStatusDto;

enum PaymentStatus {
  ACCEPTED,
  IN_PROGRESS,
  CANCELED;

  PaymentStatusDto dto() {
    return PaymentStatusDto.valueOf(name());
  }
}
