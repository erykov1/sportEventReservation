package com.sportEventReservation.payment.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentDto {
  UUID paymentId;
  String email;
  PaymentStatusDto paymentStatus;
  UUID reportId;
  UUID sportEventId;
}
