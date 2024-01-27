package com.sportEventReservation.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InstantProviderConfiguration {
  @Bean
  InstantProvider instantProvider() {
    return new InstantProvider();
  }
}
