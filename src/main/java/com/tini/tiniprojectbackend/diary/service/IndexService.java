package com.tini.tiniprojectbackend.diary.service;

import static com.tini.tiniprojectbackend.common.exception.TiniErrorCode.DIARY_NOT_FOUND;
import static com.tini.tiniprojectbackend.common.exception.TiniErrorCode.INDEX_OVER_FLOW;

import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.dto.IndexDTO;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import com.tini.tiniprojectbackend.diary.entity.IndexEntity;
import com.tini.tiniprojectbackend.diary.repository.DiaryRepository;
import com.tini.tiniprojectbackend.diary.repository.IndexRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexService {

  private final IndexRepository indexRepository;
  private final DiaryRepository diaryRepository;

  /**
   * 인덱스 생성
   * @param indexDTO
   */
  public void createIndex(IndexDTO indexDTO) {

    DiaryEntity diaryEntity = diaryRepository.getDiaryId(indexDTO.getIndexId());

    if(diaryEntity == null) {
      throw new TiniException(DIARY_NOT_FOUND);
    }

    indexRepository.save(IndexEntity.createIndexBuilder()
        .indexDTO(indexDTO).build());
  }

  /**
   * 인덱스 리스트 조회
   * @param diaryId 다이어리 아이디
   * @return
   */
  public List<IndexDTO> getIndexList(int diaryId) {

    DiaryEntity diaryEntity = diaryRepository.getDiaryId(diaryId);

    if(diaryEntity == null) {
      throw new TiniException(DIARY_NOT_FOUND);
    }

    return indexRepository.getIndexList(diaryId).stream().map(IndexDTO::from).collect(
        Collectors.toList());
  }

  /**
   *
   * @param diaryId
   * @param indexDTOList
   * @return
   */
  public void updateIndex(int diaryId, List<IndexDTO> indexDTOList) {

    if(indexDTOList.size() > 6) {
      throw new TiniException(INDEX_OVER_FLOW);
    }

    DiaryEntity diaryEntity = diaryRepository.getDiaryId(diaryId);

    if(diaryEntity == null) {
      throw new TiniException(DIARY_NOT_FOUND);
    }

    // 다이어리 인덱스 삭제
    indexRepository.deleteAll(indexRepository.getIndexList(diaryId));

    // 다이어리 인덱스 일괄 저장
    indexDTOList.forEach(indexDTO -> indexRepository.save(IndexEntity.createIndexBuilder().indexDTO(indexDTO).build()));
  }

  /**
   * 인덱스 삭제
   * @param indexId
   */
  public void deleteIndex(int indexId) {
    indexRepository.deleteById(indexId);
  }
}
