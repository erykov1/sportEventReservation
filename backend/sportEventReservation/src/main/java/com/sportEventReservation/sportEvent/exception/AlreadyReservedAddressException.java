package com.sportEventReservation.sportEvent.exception;

public class AlreadyReservedAddressException extends RuntimeException {
  public AlreadyReservedAddressException(String message) {
    super(message);
  }
}
