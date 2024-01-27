package com.sportEventReservation.sportEvent.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

interface SportEventRepository extends JpaRepository<SportEvent, UUID> {
  Optional<SportEvent> findBySportEventId(UUID sportEventId);
  Optional<SportEvent> findBySportEventAddressAndEventTime(UUID sportEventAddress, Instant eventTime);
}
