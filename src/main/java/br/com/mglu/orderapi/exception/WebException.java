package br.com.mglu.orderapi.exception;

import org.springframework.http.HttpStatus;

public class WebException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final HttpStatus status;
  private final Error error;

  public WebException(HttpStatus status, Error error) {
    this.status = status;
    this.error = error;
  }

  public WebException(HttpStatus status, Error error, Throwable throwable) {
    super(throwable);
    this.status = status;
    this.error = error;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public Error getError() {
    return error;
  }
}
