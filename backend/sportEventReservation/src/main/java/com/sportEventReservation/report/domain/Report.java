package com.sportEventReservation.report.domain;

import com.sportEventReservation.report.dto.ReportDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reports")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Report {
  @Id
  UUID reportId;
  String name;
  String surname;
  String email;
  @Enumerated(EnumType.STRING)
  ReportStatus reportStatus;
  Instant reportedAt;
  UUID sportEventId;

  ReportDto dto() {
    return ReportDto.builder()
        .reportId(reportId)
        .name(name)
        .surname(surname)
        .email(email)
        .reportStatus(reportStatus.dto())
        .reportedAt(reportedAt)
        .sportEventId(sportEventId)
        .build();
  }
}
