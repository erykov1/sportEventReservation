package com.sportEventReservation.sportEvent.domain;

import com.sportEventReservation.sportEvent.dto.SportEventDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sport_events")
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class SportEvent {
  @Id
  UUID sportEventId;
  String eventName;
  Instant eventTime;
  Instant registrationDeadline;
  String description;
  Long maxParticipants;
  @Enumerated(EnumType.STRING)
  SportEventType sportEventType;
  UUID sportEventAddress;

  SportEventDto dto() {
    return SportEventDto.builder()
        .sportEventId(sportEventId)
        .eventName(eventName)
        .eventTime(eventTime)
        .registrationDeadline(registrationDeadline)
        .description(description)
        .maxParticipants(maxParticipants)
        .sportEventType(sportEventType.dto())
        .sportEventAddress(sportEventAddress)
        .build();
  }
}
