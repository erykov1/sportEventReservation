package com.sportEventReservation.email.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EmailService {
  JavaMailSender javaMailSender;

  private List<EmailHandler> createEmailHandlers() {
    return List.of(
        new AcceptedReportEmailHandler(javaMailSender),
        new DeclinedPaymentEmailHandler(javaMailSender),
        new MaxParticipantsEmailHandler(javaMailSender)
    );
  }

  public void sendEmail(String type, String to) {
    List<EmailHandler> emailHandlers = createEmailHandlers();
    EmailHandler emailHandler = emailHandlers.stream()
        .filter(handler -> handler.supports(type))
        .findFirst().orElseThrow(IllegalArgumentException::new);
    emailHandler.sendSimpleMail(to);
  }
}
