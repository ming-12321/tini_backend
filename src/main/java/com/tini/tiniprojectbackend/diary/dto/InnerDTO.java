package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.InnerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InnerDTO extends BaseDTO {

  protected int innerThemeId;
  protected String name;
  protected int themeSavePath;

  public static InnerDTO from(InnerEntity innerEntity) {
    return InnerDTO.builder()
        .innerThemeId(innerEntity.getInnerThemeId())
        .name(innerEntity.getName())
        .themeSavePath(innerEntity.getThemeSavePath())
        .createdAt(innerEntity.getCreatedAt())
        .updatedAt(innerEntity.getUpdatedAt())
        .build();
  }

}
