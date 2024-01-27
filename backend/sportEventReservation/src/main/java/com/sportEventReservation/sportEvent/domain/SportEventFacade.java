package com.sportEventReservation.sportEvent.domain;

import com.sportEventReservation.report.dto.SportEventAssignDto;
import com.sportEventReservation.sportEvent.dto.CreateSportEventAddressDto;
import com.sportEventReservation.sportEvent.dto.CreateSportEventDto;
import com.sportEventReservation.sportEvent.dto.SportEventAddressDto;
import com.sportEventReservation.sportEvent.dto.SportEventDto;
import com.sportEventReservation.sportEvent.exception.AlreadyReservedAddressException;
import com.sportEventReservation.sportEvent.exception.NotExistingSportEventException;
import com.sportEventReservation.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SportEventFacade {
  SportEventRepository sportEventRepository;
  SportEventAddressRepository sportEventAddressRepository;
  InstantProvider instantProvider;
  SportEventPublisher sportEventPublisher;

  public SportEventAddressDto createEventAddress(CreateSportEventAddressDto createSportEventAddress) {
    if (sportEventAddressRepository.findSportEventAddressByDetails(createSportEventAddress.getPostalCode(),
        createSportEventAddress.getCity(), createSportEventAddress.getStreet(), createSportEventAddress.getStreetNumber()
    ).isPresent()) {
      return SportEventAddressDto.builder().build();
    }
    SportEventAddress sportEventAddressSave = SportEventAddress.builder()
        .sportEventAddressId(UUID.randomUUID())
        .postalCode(createSportEventAddress.getPostalCode())
        .city(createSportEventAddress.getCity())
        .street(createSportEventAddress.getStreet())
        .streetNumber(createSportEventAddress.getStreetNumber())
        .build();
    return sportEventAddressRepository.save(sportEventAddressSave).dto();
  }

  public List<SportEventAddressDto> findAllEventAddresses() {
    return sportEventAddressRepository.findAll().stream()
        .map(SportEventAddress::dto)
        .collect(Collectors.toList());
  }

  public SportEventDto findSportEventById(UUID sportEventId) {
    return sportEventRepository.findBySportEventId(sportEventId)
        .orElseThrow(() -> new NotExistingSportEventException("Sport event does not exist")).dto();
  }

  public SportEventAddressDto findSportEventAddressById(UUID sportEventAddressId) {
    return sportEventAddressRepository.findById(sportEventAddressId)
        .orElseThrow(() -> new NotExistingSportEventException("Sport event address does not exist")).dto();
  }

  public List<SportEventDto> findAllSportEventsByType(String eventType) {
    return sportEventRepository.findAll().stream()
        .filter(report -> report.dto().getSportEventType().name().equals(eventType))
        .map(SportEvent::dto)
        .collect(Collectors.toList());
  }

  public SportEventDto createSportEvent(CreateSportEventDto createSportEvent) {
    checkIfAlreadyReserved(createSportEvent);
    SportEvent sportEvent = SportEvent.builder()
        .sportEventId(UUID.randomUUID())
        .eventName(createSportEvent.getEventName())
        .eventTime(createSportEvent.getEventTime())
        .registrationDeadline(createSportEvent.getRegistrationDeadline())
        .description(createSportEvent.getDescription())
        .maxParticipants(createSportEvent.getMaxParticipants())
        .sportEventType(SportEventType.valueOf(createSportEvent.getSportEventType().name()))
        .sportEventAddress(createSportEvent.getSportEventAddress())
        .build();
    emmitEvent(sportEvent);
    return sportEventRepository.save(sportEvent).dto();
  }

  public List<SportEventDto> findAllSportEvents() {
    return sportEventRepository.findAll().stream()
        .map(SportEvent::dto)
        .collect(Collectors.toList());
  }
  private void checkIfAlreadyReserved(CreateSportEventDto createSportEvent) {
    if (sportEventRepository.findBySportEventAddressAndEventTime(createSportEvent.getSportEventAddress(),
        createSportEvent.getEventTime()).isPresent()) {
      throw new AlreadyReservedAddressException("Address has been already reserved");
    }
  }

  private void emmitEvent(SportEvent sportEvent) {
    SportEventAssignDto sportEventPublish = SportEventAssignDto.builder()
        .sportEventId(sportEvent.dto().getSportEventId())
        .maxParticipants(sportEvent.dto().getMaxParticipants())
        .registrationDeadline(sportEvent.dto().getRegistrationDeadline())
        .eventTime(sportEvent.dto().getEventTime())
        .build();
    sportEventPublisher.notifySportEventCreated(sportEventPublish);
  }
}
