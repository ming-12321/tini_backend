package com.tini.tiniprojectbackend.common.exception;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class TiniException extends RuntimeException {

  private final TiniErrorCode tiniErrorCode;


  public TiniException(TiniErrorCode tiniErrorCode) {
    super(tiniErrorCode.getMessage());
    this.tiniErrorCode = tiniErrorCode;
  }
}
