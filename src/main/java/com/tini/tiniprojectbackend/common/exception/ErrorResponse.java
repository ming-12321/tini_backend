package com.tini.tiniprojectbackend.common.exception;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

  private final String timestamp;
  private final HttpStatus status;
  private final String code;
  private final String message;

  public ErrorResponse(HttpStatus status, String code, String message) {
    this.timestamp = Instant.now().toString();
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
