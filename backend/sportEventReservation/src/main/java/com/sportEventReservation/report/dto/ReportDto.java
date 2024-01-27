package com.sportEventReservation.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportDto {
  UUID reportId;
  String name;
  String surname;
  String email;
  ReportStatusDto reportStatus;
  Instant reportedAt;
  UUID sportEventId;
}
