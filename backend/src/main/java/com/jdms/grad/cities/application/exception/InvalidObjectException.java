package com.jdms.grad.cities.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidObjectException extends RuntimeException {

  public InvalidObjectException(Object o) {
    super(String.format("Invalid object '%s'", o));
  }
}
