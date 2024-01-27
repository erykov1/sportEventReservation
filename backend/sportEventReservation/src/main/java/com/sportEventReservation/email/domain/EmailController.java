package com.sportEventReservation.email.domain;

import com.sportEventReservation.email.dto.SendEmailDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class EmailController {
  EmailService emailService;

  @PostMapping("/send")
  ResponseEntity<Void> sendMail(@RequestBody SendEmailDto sendEmail) {
    System.out.println("email " + sendEmail.getTo() + " " + sendEmail.getType());
    emailService.sendEmail(sendEmail.getType(), sendEmail.getTo());
    return ResponseEntity.ok().build();
  }
}
