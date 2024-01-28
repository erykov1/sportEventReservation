package com.sportEventReservation;

import com.sportEventReservation.sportEvent.domain.SportEventFacade;
import com.sportEventReservation.sportEvent.dto.CreateSportEventDto;
import com.sportEventReservation.sportEvent.dto.SportEventDto;
import com.sportEventReservation.sportEvent.dto.SportEventTypeDto;
import com.sportEventReservation.utils.InstantProvider;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
@Deployment(resources = "classpath*:/model/*.*")
public class SportEventReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportEventReservationApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(SportEventFacade sportEventFacade) {
		CreateSportEventDto createSportEvent = CreateSportEventDto.builder()
				.eventName("Event name")
				.eventTime(InstantProvider.fromFormatted("2024-12-24 22:00"))
				.registrationDeadline(InstantProvider.fromFormatted("2024-12-24 22:00"))
				.description("event desc")
				.maxParticipants(0L)
				.sportEventType(SportEventTypeDto.valueOf("HANDBALL"))
				.sportEventAddress(UUID.randomUUID())
				.build();
		return args -> {
			sportEventFacade.createSportEvent(createSportEvent);
		};
	}
}
