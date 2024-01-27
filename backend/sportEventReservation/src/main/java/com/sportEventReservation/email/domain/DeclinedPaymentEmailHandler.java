package com.sportEventReservation.email.domain;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@AllArgsConstructor
class DeclinedPaymentEmailHandler implements EmailHandler {
  private final static String DECLINED_PAYMENT_MESSAGE = "Twoje zglosznie zostalo odrzucone z powodu anulowania platnosci";
  private final static String DECLINED_PAYMENT_SUBJECT  = "Odrzucenie zgloszenia";
  private final JavaMailSender javaMailSender;

  @Override
  @Async
  public void sendSimpleMail(String to) {
    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom("erykmarnikzajecia@gmail.com");
      mailMessage.setTo(to);
      mailMessage.setText(DECLINED_PAYMENT_MESSAGE);
      mailMessage.setSubject(DECLINED_PAYMENT_SUBJECT);
      javaMailSender.send(mailMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean supports(String type) {
    return type.equals("DeclinedPayment");
  }
}
