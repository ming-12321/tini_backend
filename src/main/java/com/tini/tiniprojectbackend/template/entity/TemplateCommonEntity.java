package com.tini.tiniprojectbackend.template.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_TP_COMMON")
public class TemplateCommonEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "TITLE")
  private String title;
  @OneToMany(mappedBy = "templateCommon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<TemplateDiaryEntity> templateDiaryEntities;
  @OneToMany(mappedBy = "templateCommon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<PointEntity> pointEntities;
  @OneToMany(mappedBy = "templateCommon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<BudgetMonthlyEntity> budgetMonthlyEntities;
}
