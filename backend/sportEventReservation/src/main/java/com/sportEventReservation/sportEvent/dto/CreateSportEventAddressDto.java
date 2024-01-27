package com.sportEventReservation.sportEvent.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateSportEventAddressDto {
  String postalCode;
  String city;
  String street;
  String streetNumber;
}
