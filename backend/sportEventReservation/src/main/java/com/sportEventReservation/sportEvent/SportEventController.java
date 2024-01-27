package com.sportEventReservation.sportEvent;

import com.sportEventReservation.sportEvent.domain.SportEventFacade;
import com.sportEventReservation.sportEvent.dto.CreateSportEventAddressDto;
import com.sportEventReservation.sportEvent.dto.CreateSportEventDto;
import com.sportEventReservation.sportEvent.dto.SportEventAddressDto;
import com.sportEventReservation.sportEvent.dto.SportEventDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sportEvent")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SportEventController {
  SportEventFacade sportEventFacade;

  @PostMapping("/create")
  ResponseEntity<SportEventDto> createSportEvent(@RequestBody CreateSportEventDto sportEvent) {
    return ResponseEntity.ok(sportEventFacade.createSportEvent(sportEvent));
  }

  @PostMapping("/address/create")
  ResponseEntity<SportEventAddressDto> createSportEventAddress(@RequestBody CreateSportEventAddressDto eventAddress) {
    return ResponseEntity.ok(sportEventFacade.createEventAddress(eventAddress));
  }

  @GetMapping("/all")
  ResponseEntity<List<SportEventDto>> getAllSportEvents() {
    return ResponseEntity.ok(sportEventFacade.findAllSportEvents());
  }
}
