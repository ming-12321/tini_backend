package com.tini.tiniprojectbackend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {

  @ExceptionHandler(TiniException.class)
  public ResponseEntity<ErrorResponse> handleTiniException(TiniException exception) {
    TiniErrorCode tiniErrorCode = exception.getTiniErrorCode();

    ErrorResponse response = new ErrorResponse(tiniErrorCode.getStatus(), tiniErrorCode.getCode(), tiniErrorCode.getMessage());

    return new ResponseEntity<>(response, tiniErrorCode.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
    log.error("Unexpected exception", ex);
    return ResponseEntity.internalServerError()
        .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "SYS_ERROR", "서버 오류가 발생했습니다."));
  }
}
