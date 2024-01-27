package com.sportEventReservation.email.domain;

interface EmailHandler {
  void sendSimpleMail(String to);
  boolean supports(String type);
}
