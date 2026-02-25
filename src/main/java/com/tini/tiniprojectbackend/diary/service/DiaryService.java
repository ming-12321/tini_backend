package com.tini.tiniprojectbackend.diary.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import com.tini.tiniprojectbackend.diary.enumeration.Position;
import com.tini.tiniprojectbackend.diary.repository.DiaryRepository;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

  private final DiaryRepository diaryRepository;
  private final UserRepository userRepository;

  /**
   * 다이어리 생성
   * @param diaryDTO 생성할 다이어리 정보
   * @return 생성된 다이어리 정보
   */
  @Transactional
  public DiaryDTO createDiary(DiaryDTO diaryDTO) {
    UserEntity userEntity = userRepository.findById(diaryDTO.getUserUuid())
        .orElseThrow(() -> new TiniException(TiniErrorCode.USER_NOT_FOUND));

    DiaryEntity diaryEntity = DiaryEntity.builder()
        .diaryId(diaryDTO.getDiaryId())
        .name(diaryDTO.getName())
        .position(diaryDTO.getPosition())
        .rowIndex(diaryDTO.getRowIndex())
        .calIndex(diaryDTO.getCalIndex())
        .isMain(diaryDTO.getIsMain())
        .user(userEntity)
        .build();

    try {
      DiaryEntity savedEntity = diaryRepository.save(diaryEntity);
      return DiaryDTO.from(savedEntity);
    } catch (Exception e) {
      log.error("다이어리 생성 실패: {}", e.getMessage(), e);
      throw new TiniException(TiniErrorCode.DIARY_CREATE_FAILED);
    }
  }

  /**
   * 다이어리 단일 조회
   * @param diaryId 다이어리 아이디
   * @return 다이어리 정보
   */
  @Transactional(readOnly = true)
  public DiaryDTO getDiary(int diaryId) {
    DiaryEntity diaryEntity = diaryRepository.getDiaryId(diaryId);
    if (diaryEntity == null) {
      throw new TiniException(TiniErrorCode.DIARY_NOT_FOUND);
    }
    return DiaryDTO.from(diaryEntity);
  }

  /**
   * 사용자별 다이어리 목록 조회
   * @param userUuid 사용자 UUID
   * @return 다이어리 목록 정보
   */
  @Transactional(readOnly = true)
  public List<DiaryDTO> getDiariesByUser(String userUuid) {
    userRepository.findById(userUuid)
        .orElseThrow(() -> new TiniException(TiniErrorCode.DIARY_NOT_FOUND));

    return diaryRepository.getDiariesByUserUuid(userUuid).stream()
        .map(DiaryDTO::from)
        .toList();
  }

  /**
   * 다이어리 단일 수정
   * @param diaryId 다이어리 아이디
   * @param diaryDTO 수정할 다이어리 내용
   * @return 수정된 다이어리 정보
   */
  @Transactional
  public DiaryDTO updateDiary(int diaryId, DiaryDTO diaryDTO) {
    DiaryEntity diaryEntity = diaryRepository.getDiaryId(diaryId);
    if (diaryEntity == null) {
      throw new TiniException(TiniErrorCode.DIARY_NOT_FOUND);
    }

    try {
      diaryEntity.updateDiary(diaryDTO);
      return DiaryDTO.from(diaryEntity);
    } catch (Exception e) {
      log.error("다이어리 수정 실패: {}", e.getMessage(), e);
      throw new TiniException(TiniErrorCode.DIARY_UPDATE_FAILED);
    }
  }

  /**
   * 다이어리 위치 일괄 저장
   * @param userUuid 사용자 UUID
   * @param positions 다이어리 ID와 위치 정보(position, rowIndex, calIndex)를 담은 목록
   * @return 위치 업데이트 후 사용자의 전체 다이어리 목록
   */
  @Transactional
  public List<DiaryDTO> updatePositions(String userUuid, List<DiaryDTO> positions) {
    userRepository.findById(userUuid)
        .orElseThrow(() -> new TiniException(TiniErrorCode.USER_NOT_FOUND));

    List<DiaryEntity> allDiaries = diaryRepository.getDiariesByUserUuid(userUuid);
    Map<Integer, DiaryEntity> diaryMap = allDiaries.stream()
        .collect(Collectors.toMap(DiaryEntity::getDiaryId, d -> d));

    try {
      for (DiaryDTO dto : positions) {
        DiaryEntity diary = diaryMap.get(dto.getDiaryId());
        if (diary == null) {
          throw new TiniException(TiniErrorCode.DIARY_NOT_FOUND);
        }
        diary.moveTo(dto.getPosition(), dto.getRowIndex(), dto.getCalIndex());
      }
    } catch (TiniException e) {
      throw e;
    } catch (Exception e) {
      log.error("다이어리 위치 저장 실패: {}", e.getMessage(), e);
      throw new TiniException(TiniErrorCode.DIARY_MOVE_FAILED);
    }

    // 대표 다이어리 자동 설정/해제 (DESK rowIndex=0, calIndex=-1 위치 기준)
    DiaryEntity mainCandidate = allDiaries.stream()
        .filter(d -> d.getPosition() == Position.DESK
            && d.getRowIndex() == 0
            && d.getCalIndex() == -1)
        .findFirst()
        .orElse(null);

    allDiaries.forEach(d -> {
      int expected = (mainCandidate != null && d.getDiaryId() == mainCandidate.getDiaryId()) ? 1 : 0;
      if (d.getIsMain() != expected) {
        d.updateIsMain(expected);
      }
    });

    return allDiaries.stream()
        .sorted(Comparator.comparing(DiaryEntity::getPosition)
            .thenComparingInt(DiaryEntity::getRowIndex)
            .thenComparingInt(DiaryEntity::getCalIndex))
        .map(DiaryDTO::from)
        .toList();
  }

  /**
   * 다이어리 삭제
   * @param diaryId 다이어리 아이디
   * @param userUuid 사용자 UUID
   * @return 삭제 후 재정렬된 나머지 다이어리 목록
   */
  @Transactional
  public List<DiaryDTO> deleteDiary(int diaryId, String userUuid) {
    userRepository.findById(userUuid)
        .orElseThrow(() -> new TiniException(TiniErrorCode.USER_NOT_FOUND));

    List<DiaryEntity> allDiaries = diaryRepository.getDiariesByUserUuid(userUuid);

    DiaryEntity targetDiary = allDiaries.stream()
        .filter(d -> d.getDiaryId() == diaryId)
        .findFirst()
        .orElseThrow(() -> new TiniException(TiniErrorCode.DIARY_NOT_FOUND));

    try {
      allDiaries.remove(targetDiary);
      diaryRepository.delete(targetDiary);

      List<DiaryEntity> deskDiaries = partitionSorted(allDiaries, Position.DESK);
      List<DiaryEntity> bookcaseDiaries = partitionSorted(allDiaries, Position.BOOKCASE);

      normalizeDeskIndices(deskDiaries);
      normalizeBookcaseIndices(bookcaseDiaries);

      return toSortedDTOList(allDiaries);
    } catch (TiniException e) {
      throw e;
    } catch (Exception e) {
      log.error("다이어리 삭제 실패: {}", e.getMessage(), e);
      throw new TiniException(TiniErrorCode.DIARY_DELETE_FAILED);
    }
  }

  // === private: normalize (정합성 보정) ===

  /**
   * DESK 위치 다이어리 rowIndex 재정렬(0부터 순차)
   * @param deskDiaries DESK 위치의 다이어리 목록 (rowIndex 오름차순 정렬된 상태)
   */
  private void normalizeDeskIndices(List<DiaryEntity> deskDiaries) {
    for (int i = 0; i < deskDiaries.size(); i++) {
      deskDiaries.get(i).moveTo(Position.DESK, i, -1);
    }
  }

  /**
   * BOOKCASE 위치 다이어리 row/calIndex 재정렬 (3열 기준)
   * @param bookcaseDiaries BOOKCASE 위치의 다이어리 목록 (rowIndex*3+calIndex 오름차순 정렬된 상태)
   */
  private void normalizeBookcaseIndices(List<DiaryEntity> bookcaseDiaries) {
    for (int i = 0; i < bookcaseDiaries.size(); i++) {
      bookcaseDiaries.get(i).moveTo(Position.BOOKCASE, i / 3, i % 3);
    }
  }

  // === private: 공통 유틸 ===

  /**
   * 특정 Position으로 필터링 후 정렬된 목록 반환
   * @param allDiaries 전체 다이어리 목록
   * @param position   필터링할 위치 (DESK: rowIndex 기준, BOOKCASE: rowIndex*3+calIndex 기준 정렬)
   * @return 해당 위치의 정렬된 다이어리 목록
   */
  private List<DiaryEntity> partitionSorted(List<DiaryEntity> allDiaries, Position position) {
    return allDiaries.stream()
        .filter(d -> d.getPosition() == position)
        .sorted(position == Position.DESK
            ? Comparator.comparingInt(DiaryEntity::getRowIndex)
            : Comparator.comparingInt(d -> d.getRowIndex() * 3 + d.getCalIndex()))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * position -> rowIndex -> calIndex 순으로 정렬된 DTO 목록 반환
   * @param allDiaries 변환할 다이어리 목록
   * @return 정렬된 DiaryDTO 목록
   */
  private List<DiaryDTO> toSortedDTOList(List<DiaryEntity> allDiaries) {
    return allDiaries.stream()
        .sorted(Comparator
            .comparing(DiaryEntity::getPosition)
            .thenComparingInt(DiaryEntity::getRowIndex)
            .thenComparingInt(DiaryEntity::getCalIndex))
        .map(DiaryDTO::from)
        .toList();
  }
}