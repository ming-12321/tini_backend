package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.IndexDTO;
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
@Entity(name = "TB_INDEX")
public class IndexEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "INDEX_ID")
  private int indexId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAGE_ID")
  private PageEntity page;

  @Builder(builderMethodName = "createIndexBuilder", builderClassName = "createIndexBuilder")
  public IndexEntity(IndexDTO indexDTO, PageDTO pageDTO) {
    this.page = pageDTO.getPageId() != 0
        ? PageEntity.builder().pageId(pageDTO.getPageId()).build() : null;
  }
}
