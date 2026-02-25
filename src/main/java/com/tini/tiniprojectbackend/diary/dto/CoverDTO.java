package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.CoverEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoverDTO extends BaseDTO {

  protected int coverThemeId;
  protected String name;
  protected int themeSavePath;

  public static CoverDTO from(CoverEntity coverEntity) {
    return CoverDTO.builder()
        .coverThemeId(coverEntity.getCoverThemeId())
        .name(coverEntity.getName())
        .themeSavePath(coverEntity.getThemeSavePath())
        .createdAt(coverEntity.getCreatedAt())
        .updatedAt(coverEntity.getUpdatedAt())
        .build();
  }

}