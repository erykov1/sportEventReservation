package com.sportEventReservation.sportEvent.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateSportEventDto {
  String eventName;
  Instant eventTime;
  Instant registrationDeadline;
  String description;
  Long maxParticipants;
  SportEventTypeDto sportEventType;
  UUID sportEventAddress;
}
