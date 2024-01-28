package com.sportEventReservation.email.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
class JavaMailSenderBean {
  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(465);

    javaMailSender.setUsername("erykmarnikzajecia@gmail.com");
    javaMailSender.setPassword("vsspwwjbvfwrgqoa");

    Properties props = javaMailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.smtp.starttls.enable", "true");

    return javaMailSender;
  }
}
