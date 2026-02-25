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
  protected int innerThemeId;
  protected int coverThemeId;
  protected int diaryId;
  protected PageType pageType;

  public static PageDTO from(PageEntity pageEntity) {
    return PageDTO.builder()
        .pageId(pageEntity.getPageId())
        .innerThemeId(pageEntity.getInner() != null ? pageEntity.getInner().getInnerThemeId() : 0)
        .coverThemeId(pageEntity.getCover() != null ? pageEntity.getCover().getCoverThemeId() : 0)
        .diaryId(pageEntity.getDiary().getDiaryId())
        .pageType(pageEntity.getPageType())
        .createdAt(pageEntity.getCreatedAt())
        .updatedAt(pageEntity.getUpdatedAt())
        .build();
  }

}