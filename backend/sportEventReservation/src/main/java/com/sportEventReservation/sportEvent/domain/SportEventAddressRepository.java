package com.sportEventReservation.sportEvent.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface SportEventAddressRepository extends JpaRepository<SportEventAddress, UUID> {
  @Query("SELECT sea FROM SportEventAddress sea WHERE sea.postalCode = :postalCode " +
      "AND sea.city = :city AND sea.street = :street AND sea.streetNumber = :streetNumber")
  Optional<SportEventAddress> findSportEventAddressByDetails(@Param("postalCode") String postalCode,
                                                             @Param("city") String city,
                                                             @Param("street") String street,
                                                             @Param("streetNumber") String streetNumber
  );
}
