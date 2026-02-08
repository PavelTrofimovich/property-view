package com.example.gptechtask.exception.exceptions;

public class UnsupportedHistogramParameterException extends RuntimeException {

  public UnsupportedHistogramParameterException(String param) {
    super("Unsupported histogram parameter: " + param);
  }
}
