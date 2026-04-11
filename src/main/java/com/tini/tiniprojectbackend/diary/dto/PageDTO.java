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
  protected InnerDTO innerTheme;
  protected CoverDTO coverTheme;
  protected BookmarkDTO bookmark;
  protected DiaryDTO diary;
  protected int diaryPage;
  protected PageType pageType;

  public static PageDTO from(PageEntity pageEntity) {
    return PageDTO.builder()
        .pageId(pageEntity.getPageId())
        .bookmark(pageEntity.getBookmark() != null ? BookmarkDTO.from(pageEntity.getBookmark()) : null)
        .innerTheme(pageEntity.getInner() != null ? InnerDTO.from(pageEntity.getInner()) : null)
        .coverTheme(pageEntity.getCover() != null ? CoverDTO.from(pageEntity.getCover()) : null)
        .diary(DiaryDTO.from(pageEntity.getDiary()))
        .diaryPage(pageEntity.getDiaryPage())
        .pageType(pageEntity.getPageType())
        .createdAt(pageEntity.getCreatedAt())
        .updatedAt(pageEntity.getUpdatedAt())
        .build();
  }

}