package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.ThemeEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO extends BaseDTO {

  protected int themeId;
  protected String name;
  protected String themeSavePath;
  protected PageType pageType;


  public static ThemeDTO from(ThemeEntity themeEntity) {
    return ThemeDTO.builder()
        .themeId(themeEntity.getThemeId())
        .name(themeEntity.getName())
        .themeSavePath(themeEntity.getThemeSavePath())
        .pageType(themeEntity.getPageType())
        .createdAt(themeEntity.getCreatedAt())
        .updatedAt(themeEntity.getUpdatedAt())
        .build();
  }
}
