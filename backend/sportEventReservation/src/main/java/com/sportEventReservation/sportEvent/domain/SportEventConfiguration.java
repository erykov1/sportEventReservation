package com.sportEventReservation.sportEvent.domain;

import com.sportEventReservation.utils.InstantProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SportEventConfiguration {
  @Bean
  SportEventFacade sportEventFacade(SportEventRepository sportEventRepository, SportEventAddressRepository sportEventAddressRepository,
    ApplicationEventPublisher eventPublisher
  ) {
    return SportEventFacade.builder()
        .sportEventRepository(sportEventRepository)
        .sportEventAddressRepository(sportEventAddressRepository)
        .sportEventPublisher(new SportEventPublisher(eventPublisher))
        .instantProvider(new InstantProvider())
        .build();
  }
}
