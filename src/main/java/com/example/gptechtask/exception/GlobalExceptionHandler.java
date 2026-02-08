package com.example.gptechtask.exception;

import com.example.gptechtask.exception.exceptions.NotFoundException;
import com.example.gptechtask.exception.exceptions.UnsupportedHistogramParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(ex.getMessage()));
  }

  @ExceptionHandler(UnsupportedHistogramParameterException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedHistogramParam(
      UnsupportedHistogramParameterException ex
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(ex.getMessage()));
  }
}
