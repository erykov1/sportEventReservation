package com.sportEventReservation.sportEvent.domain;

import com.sportEventReservation.sportEvent.dto.SportEventAddressDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Entity
@Table(name = "sport_events_addresses")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class SportEventAddress {
  @Id
  UUID sportEventAddressId;
  String postalCode;
  String city;
  String street;
  String streetNumber;

  SportEventAddressDto dto() {
    return SportEventAddressDto.builder()
        .sportEventAddressId(sportEventAddressId)
        .postalCode(postalCode)
        .city(city)
        .street(street)
        .streetNumber(streetNumber)
        .build();
  }
}
