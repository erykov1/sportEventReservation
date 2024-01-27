package com.sportEventReservation.email.domain;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@AllArgsConstructor
class AcceptedReportEmailHandler implements EmailHandler {
  private final static String ACCEPTED_MESSAGE = "Twoje zgloszenie zostalo zaakceptowane";
  private final static String ACCEPTED_SUBJECT  = "Akceptacja zgloszenia";
  private final JavaMailSender javaMailSender;

  @Override
  @Async
  public void sendSimpleMail(String to) {
    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom("erykmarnikzajecia@gmail.com");
      mailMessage.setTo(to);
      mailMessage.setText(ACCEPTED_MESSAGE);
      mailMessage.setSubject(ACCEPTED_SUBJECT);
      javaMailSender.send(mailMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean supports(String type) {
    return type.equals("Accepted");
  }
}
