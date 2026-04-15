package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.PageDTO;
import com.tini.tiniprojectbackend.diary.dto.ThemeDTO;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_PAGE")
public class PageEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PAGE_ID")
  private int pageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "THEME_ID")
  private ThemeEntity theme;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DIARY_ID", nullable = false)
  private DiaryEntity diary;

  @Column(name = "DIARY_PAGE", nullable = false)
  private int diaryPage;

  @Column(name = "POSITION", nullable = false)
  @Enumerated(EnumType.STRING)
  private PageType pageType;

  @Builder(builderMethodName = "createPageBuilder", builderClassName = "createPageBuilder")
  public PageEntity(PageDTO pageDTO, UserDTO userDTO) {
    this.theme = pageDTO.getTheme() != null
        ? ThemeEntity.createThemeBuilder().themeDTO(pageDTO.getTheme()).build() : null;
    this.diary = DiaryEntity.createDiaryBuilder().diaryDTO(pageDTO.getDiary()).userDTO(userDTO).build();
    this.diaryPage = pageDTO.getDiaryPage();
    this.pageType = pageDTO.getPageType();
  }

  public void updateTheme(ThemeDTO themeDTO) {
    this.theme = ThemeEntity.createThemeBuilder().themeDTO(themeDTO).build();
  }

}
