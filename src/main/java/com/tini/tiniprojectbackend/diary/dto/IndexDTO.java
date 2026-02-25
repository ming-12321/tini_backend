package com.tini.tiniprojectbackend.diary.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import com.tini.tiniprojectbackend.diary.entity.IndexEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IndexDTO extends BaseDTO {

  protected int indexId;
  protected int page;

  public static IndexDTO from(IndexEntity indexEntity) {
    return IndexDTO.builder()
        .indexId(indexEntity.getIndexId())
        .page(indexEntity.getPage() != null ? indexEntity.getPage().getPageId() : 0)
        .createdAt(indexEntity.getCreatedAt())
        .updatedAt(indexEntity.getUpdatedAt())
        .build();
  }

}