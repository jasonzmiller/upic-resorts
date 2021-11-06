package com.cs6550.upicresortsserver.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException(String message) {
    super(message);
  }

  public InvalidCredentialsException() {
    this("Invalid credentials, user not authorized. Please supply the authorization header.");
  }
}
