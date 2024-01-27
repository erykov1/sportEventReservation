package com.sportEventReservation.report.domain;

import com.sportEventReservation.report.dto.SportEventAssignDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sport_events_assign")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class SportEventAssign {
  @Id
  UUID sportEventId;
  Long maxParticipants;
  Instant registrationDeadline;
  Instant eventTime;

  SportEventAssignDto dto() {
    return SportEventAssignDto.builder()
        .sportEventId(sportEventId)
        .maxParticipants(maxParticipants)
        .registrationDeadline(registrationDeadline)
        .eventTime(eventTime)
        .build();
  }
}
