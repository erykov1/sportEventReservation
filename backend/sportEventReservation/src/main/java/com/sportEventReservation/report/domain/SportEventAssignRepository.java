package com.sportEventReservation.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface SportEventAssignRepository extends JpaRepository<SportEventAssign, UUID> {
  Optional<SportEventAssign> findSportEventBySportEventId(UUID sportEventId);
}
