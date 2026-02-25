package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import com.tini.tiniprojectbackend.diary.enumeration.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO extends BaseDTO {

  protected int diaryId;
  protected String name;
  protected Position position;
  protected int rowIndex;
  protected int calIndex;
  protected int isMain;
  protected String userUuid;

  public static DiaryDTO from(DiaryEntity diaryEntity) {
    return DiaryDTO.builder()
        .diaryId(diaryEntity.getDiaryId())
        .name(diaryEntity.getName())
        .position(diaryEntity.getPosition())
        .rowIndex(diaryEntity.getRowIndex())
        .calIndex(diaryEntity.getCalIndex())
        .isMain(diaryEntity.getIsMain())
        .userUuid(diaryEntity.getUser().getUserUuid())
        .createdAt(diaryEntity.getCreatedAt())
        .updatedAt(diaryEntity.getUpdatedAt())
        .build();
  }

}