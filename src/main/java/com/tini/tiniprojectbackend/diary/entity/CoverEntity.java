package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.CoverDTO;
import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
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
@Entity(name = "TB_COVER_THEME")
public class CoverEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "COVER_THEME_ID")
  private int coverThemeId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "THEME_SAVE_PATH", nullable = false)
  private int themeSavePath;

  @Builder(builderMethodName = "createCoverBuilder", builderClassName = "createCoverBuilder")
  public CoverEntity(CoverDTO coverDTO, DiaryDTO diaryDTO) {
    this.name = coverDTO.getName();
    this.themeSavePath = coverDTO.getThemeSavePath();
  }
}