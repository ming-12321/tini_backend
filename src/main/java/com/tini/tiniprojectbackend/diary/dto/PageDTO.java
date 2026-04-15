package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.PageEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO extends BaseDTO {

  protected int pageId;
  protected ThemeDTO theme;
  protected DiaryDTO diary;
  protected int diaryPage;
  protected PageType pageType;

  public static PageDTO from(PageEntity pageEntity) {
    return PageDTO.builder()
        .pageId(pageEntity.getPageId())
        .theme(pageEntity.getTheme() != null ? ThemeDTO.from(pageEntity.getTheme()) : null)
        .diary(DiaryDTO.from(pageEntity.getDiary()))
        .diaryPage(pageEntity.getDiaryPage())
        .pageType(pageEntity.getPageType())
        .createdAt(pageEntity.getCreatedAt())
        .updatedAt(pageEntity.getUpdatedAt())
        .build();
  }

}