package com.tini.tiniprojectbackend.template.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import com.tini.tiniprojectbackend.template.entity.TemplateDiaryEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDiaryDTO extends BaseDTO {

  protected int id;
  protected int diaryPage;
  protected LocalDateTime customTime;
  protected DiaryDTO diary;
  protected TemplateCommonDTO templateCommon;
  // 타임 트래커 DTO
  // 북 트래커 DTO

  public static TemplateDiaryDTO from(TemplateDiaryEntity templateDiaryEntity) {
    return TemplateDiaryDTO.builder()
        .id(templateDiaryEntity.getId())
        .diaryPage(templateDiaryEntity.getDiaryPage())
        .customTime(templateDiaryEntity.getCustomTime())
        .diary(DiaryDTO.from(templateDiaryEntity.getDiary()))
        .templateCommon(TemplateCommonDTO.from(templateDiaryEntity.getTemplateCommon()))
        .updatedAt(templateDiaryEntity.getUpdatedAt())
        .createdAt(templateDiaryEntity.getCreatedAt())
        .build();
  }

}
