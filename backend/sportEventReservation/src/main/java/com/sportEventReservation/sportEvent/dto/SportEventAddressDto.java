package com.sportEventReservation.sportEvent.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SportEventAddressDto {
  UUID sportEventAddressId;
  String postalCode;
  String city;
  String street;
  String streetNumber;
}
