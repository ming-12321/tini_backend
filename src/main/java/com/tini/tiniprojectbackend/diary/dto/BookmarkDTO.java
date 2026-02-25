package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.BookmarkEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDTO extends BaseDTO {

  protected int bookmarkId;
  protected int pageId;

  public static BookmarkDTO from(BookmarkEntity bookmarkEntity) {
    return BookmarkDTO.builder()
        .bookmarkId(bookmarkEntity.getBookmarkId())
        .pageId(bookmarkEntity.getPage().getPageId())
        .createdAt(bookmarkEntity.getCreatedAt())
        .updatedAt(bookmarkEntity.getUpdatedAt())
        .build();
  }

}