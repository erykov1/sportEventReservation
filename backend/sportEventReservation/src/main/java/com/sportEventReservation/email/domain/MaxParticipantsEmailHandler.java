package com.sportEventReservation.email.domain;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@AllArgsConstructor
class MaxParticipantsEmailHandler implements EmailHandler {
  private final static String MAX_PARTICIPANTS_MESSAGE = "Twoje zglosznie zostalo odrzucone z maksymalnej liczby uczestnikow";
  private final static String DECLINED_SUBJECT  = "Odrzucenie zgloszenia";
  private final JavaMailSender javaMailSender;

  @Override
  @Async
  public void sendSimpleMail(String to) {
    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom("erykmarnikzajecia@gmail.com");
      mailMessage.setTo(to);
      mailMessage.setText(MAX_PARTICIPANTS_MESSAGE);
      mailMessage.setSubject(DECLINED_SUBJECT);
      javaMailSender.send(mailMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean supports(String type) {
    return type.equals("MaxParticipants");
  }
}
