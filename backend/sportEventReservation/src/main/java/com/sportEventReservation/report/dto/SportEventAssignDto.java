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
public class SportEventAssignDto {
  UUID sportEventId;
  Long maxParticipants;
  Instant registrationDeadline;
  Instant eventTime;
}
