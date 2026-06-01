package com.tini.tiniprojectbackend.template.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.template.entity.TemplateCommonEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateCommonDTO extends BaseDTO {

  protected int id;
  protected String title;
  protected List<TemplateDiaryDTO> templateDiaryDTOS;
  // 데일리 포인트
  // 가계부

  public static TemplateCommonDTO from(TemplateCommonEntity templateCommonEntity) {
    return TemplateCommonDTO.builder()
        .id(templateCommonEntity.getId())
        .title(templateCommonEntity.getTitle())
        .templateDiaryDTOS(templateCommonEntity.getTemplateDiaryEntities().stream().map(TemplateDiaryDTO::from).collect(
            Collectors.toList()))
        .updatedAt(templateCommonEntity.getUpdatedAt())
        .createdAt(templateCommonEntity.getCreatedAt())
        .build();
  }
}
