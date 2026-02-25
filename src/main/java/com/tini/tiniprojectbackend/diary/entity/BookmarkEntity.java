package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.BookmarkDTO;
import com.tini.tiniprojectbackend.diary.dto.PageDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "TB_BOOKMARK")
public class BookmarkEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BOOKMARK_ID")
  private int bookmarkId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAGE_ID", nullable = false)
  private PageEntity page;

  @Builder(builderMethodName = "createBookmarkBuilder", builderClassName = "createBookmarkBuilder")
  public BookmarkEntity(BookmarkDTO bookmarkDTO, PageDTO pageDTO) {
    this.page = PageEntity.builder().pageId(pageDTO.getPageId()).build();
  }

}
