package com.tini.tiniprojectbackend.diary.controller;

import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.service.DiaryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tini/diary")
public class DiaryController {

  private final DiaryService diaryService;

  /**
   * 다이어리 생성
   * @param diaryDTO 생성할 다이어리 정보
   * @return 생성된 다이어리 정보
   */
  @PostMapping
  public ResponseEntity<DiaryDTO> createDiary(@RequestBody DiaryDTO diaryDTO) {
    return new ResponseEntity<>(diaryService.createDiary(diaryDTO), HttpStatus.CREATED);
  }

  /**
   * 다이어리 단일 조회
   * @param diaryId 다이어리 아이디
   * @return 다이어리 정보
   */
  @GetMapping("/{diaryId}")
  public ResponseEntity<DiaryDTO> getDiary(@PathVariable int diaryId) {
    return new ResponseEntity<>(diaryService.getDiary(diaryId), HttpStatus.OK);
  }

  /**
   * 사용자별 다이어리 조회
   * @param userUuid 사용자 UUID
   * @return 다이어리 목록 정보
   */
  @GetMapping("/user/{userUuid}")
  public ResponseEntity<List<DiaryDTO>> getDiariesByUser(@PathVariable String userUuid) {
    return new ResponseEntity<>(diaryService.getDiariesByUser(userUuid), HttpStatus.OK);
  }

  /**
   * 다이어리 수정
   * @param diaryId 다이어리 아이디
   * @param diaryDTO 수정할 다이어리 내용
   * @return 수정된 다이어리 정보
   */
  @PatchMapping("/{diaryId}")
  public ResponseEntity<DiaryDTO> updateDiary(@PathVariable int diaryId, @RequestBody DiaryDTO diaryDTO) {
    return new ResponseEntity<>(diaryService.updateDiary(diaryId, diaryDTO), HttpStatus.OK);
  }

  /**
   * 다이어리 위치 일괄 저장
   * @param userUuid 사용자 UUID
   * @param positions 다이어리 ID와 위치 정보(position, rowIndex, calIndex)를 담은 목록
   * @return 위치 업데이트 후 사용자의 전체 다이어리 목록
   */
  @PatchMapping("/positions")
  public ResponseEntity<List<DiaryDTO>> updatePositions(@RequestParam String userUuid, @RequestBody List<DiaryDTO> positions) {
    return new ResponseEntity<>(diaryService.updatePositions(userUuid, positions), HttpStatus.OK);
  }

  /**
   * 다이어리 삭제
   * @param diaryId 다이어리 아이디
   * @param userUuid 사용자 UUID
   * @return 삭제 후 재정렬된 나머지 다이어리 목록
   */
  @DeleteMapping("/{diaryId}")
  public ResponseEntity<List<DiaryDTO>> deleteDiary(@PathVariable int diaryId, @RequestParam String userUuid) {
    return new ResponseEntity<>(diaryService.deleteDiary(diaryId, userUuid), HttpStatus.OK);
  }

}
