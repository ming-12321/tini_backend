package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.ThemeDTO;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_THEME")
public class ThemeEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "THEME_ID")
  private int themeId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "THEME_SAVE_PATH", nullable = false)
  private String themeSavePath;

  @Column(name = "PAGE_TYPE")
  private PageType pageType;

  @Builder(builderMethodName = "createThemeBuilder", builderClassName = "createThemeBuilder")
  public ThemeEntity(ThemeDTO themeDTO) {
    this.name = themeDTO.getName();
    this.themeSavePath = themeDTO.getThemeSavePath();
    this.pageType = themeDTO.getPageType();
  }

  public void updateTheme(ThemeDTO themeDTO) {
    this.name =  themeDTO.getName();
    this.themeSavePath = themeDTO.getThemeSavePath();
  }
}
