package br.com.mglu.orderapi.exception;

public class ErrorResponse {

  private final Error error;

  public ErrorResponse(Error error) {
    this.error = error;
  }

  public Error getError() {
    return error;
  }
}
