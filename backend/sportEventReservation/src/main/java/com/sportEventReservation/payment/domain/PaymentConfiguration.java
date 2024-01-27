package com.sportEventReservation.payment.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaymentConfiguration {
  @Bean
  PaymentFacade paymentFacade(PaymentRepository paymentRepository) {
    return new PaymentFacade(paymentRepository);
  }
}
