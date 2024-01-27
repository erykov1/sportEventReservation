package com.sportEventReservation.sportEvent.exception;

public class NotExistingSportEventException extends RuntimeException {
  public NotExistingSportEventException(String message) {
    super(message);
  }
}
