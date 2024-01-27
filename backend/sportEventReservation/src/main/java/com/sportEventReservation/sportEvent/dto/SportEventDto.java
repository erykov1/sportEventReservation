package com.sportEventReservation.sportEvent.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SportEventDto {
  UUID sportEventId;
  String eventName;
  Instant eventTime;
  Instant registrationDeadline;
  String description;
  Long maxParticipants;
  SportEventTypeDto sportEventType;
  UUID sportEventAddress;
}
