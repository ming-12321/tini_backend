package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.dto.InnerDTO;
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
@Entity(name = "TB_INNER_THEME")
public class InnerEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "INNER_THEME_ID")
  private int innerThemeId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "THEME_SAVE_PATH", nullable = false)
  private int themeSavePath;


  @Builder(builderMethodName = "createPageBuilder", builderClassName = "createPageBuilder")
  public InnerEntity(InnerDTO innerDTO) {
    this.name = innerDTO.getName();
    this.themeSavePath = innerDTO.getThemeSavePath();
  }

}