package com.sportEventReservation.sportEvent.domain;

import com.sportEventReservation.sportEvent.dto.SportEventTypeDto;

enum SportEventType {
  HANDBALL,
  FOOTBALL,
  VOLLEYBALL;

  SportEventTypeDto dto() {
    return SportEventTypeDto.valueOf(name());
  }
}
