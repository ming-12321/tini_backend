package com.tini.tiniprojectbackend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TiniErrorCode {

  // USER
  USER_NOT_FOUND(HttpStatus.NOT_FOUND,"U0001","해당 사용자를 찾을 수 없습니다."),
  // DIARY
  DIARY_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"D0001","다이어리를 생성하지 못했습니다."),
  DIARY_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"D0002","다이어리 삭제에 실패했습니다."),
  DIARY_NOT_FOUND(HttpStatus.NOT_FOUND,"D0003","다이어리 조회에 실패했습니다."),
  DIARY_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"D0004","다이어리 수정에 실패했습니다."),
  DIARY_MOVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"D0005","다이어리 이동에 실패했습니다."),
  // INDEX
  INDEX_OVER_FLOW(HttpStatus.INTERNAL_SERVER_ERROR, "D0008","인덱스 개수가 넘어갔습니다.");


  private final HttpStatus status;
  private final String code;
  private final String message;
}
