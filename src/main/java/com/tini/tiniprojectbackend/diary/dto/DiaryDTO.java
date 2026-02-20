package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import com.tini.tiniprojectbackend.diary.enumeration.Position;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO {

  protected int diaryId;
  protected String name;
  protected Position position;
  protected int rowIndex;
  protected int calIndex;
  protected int isMain;
  protected String userUuid;
  protected LocalDateTime updatedAt;
  protected LocalDateTime createdAt;

  @Builder(builderMethodName = "toDiaryBuilder", builderClassName = "toDiaryBuilder")
  public DiaryDTO(DiaryEntity diaryEntity){
    this.diaryId = diaryEntity.getDiaryId();
    this.name = diaryEntity.getName();
    this.position = diaryEntity.getPosition();
    this.rowIndex = diaryEntity.getRowIndex();
    this.calIndex = diaryEntity.getCalIndex();
    this.isMain = diaryEntity.getIsMain();
    this.userUuid = diaryEntity.getUser().getUserUuid();
    this.updatedAt = diaryEntity.getUpdatedAt();
    this.createdAt = diaryEntity.getCreatedAt();
  }

}
