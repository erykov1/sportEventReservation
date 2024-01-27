package com.sportEventReservation.sportEvent.domain;

import com.sportEventReservation.report.dto.SportEventAssignDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SportEventPublisher {
  ApplicationEventPublisher applicationEventPublisher;

  void notifySportEventCreated(SportEventAssignDto sportEventPublish) {
    applicationEventPublisher.publishEvent(sportEventPublish);
  }
}
