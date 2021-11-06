package com.cs6550.upicresortsserver.exceptionhandlers;

import com.cs6550.upicresortsserver.exceptions.BadRequestException;
import com.cs6550.upicresortsserver.exceptions.EntityNotFoundException;
import com.cs6550.upicresortsserver.exceptions.InvalidCredentialsException;
import com.cs6550.upicresortsserver.models.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ResponseMsg> handleEntityNotFound(EntityNotFoundException ex) {
    return new ResponseEntity<>(new ResponseMsg(ex.getMessage()), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<ResponseMsg> handleBadRequest(BadRequestException ex) {
    return new ResponseEntity<>(new ResponseMsg(ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  protected ResponseEntity<ResponseMsg> handleInvalidCredentials(InvalidCredentialsException ex) {
    return new ResponseEntity<>(new ResponseMsg(ex.getMessage()), HttpStatus.UNAUTHORIZED);
  }
}
