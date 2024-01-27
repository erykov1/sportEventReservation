package com.sportEventReservation.report.domain;

import com.sportEventReservation.utils.InstantProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReportConfiguration {
  @Bean
  ReportFacade reportFacade(ReportRepository reportRepository, SportEventAssignRepository sportEventAssignRepository) {
    return ReportFacade.builder()
        .reportRepository(reportRepository)
        .sportEventAssignRepository(sportEventAssignRepository)
        .instantProvider(new InstantProvider())
        .build();
  }
}
