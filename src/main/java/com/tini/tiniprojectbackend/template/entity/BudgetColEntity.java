package com.tini.tiniprojectbackend.template.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
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
@Entity(name = "TB_BUDGET_COL")
public class BudgetColEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "CATEGORY")
  private String category;
  @Column(name = "CELL")
  private int cell;
  @Column(name = "BILL")
  private int bill;
  @Column(name = "COIN")
  private int coin;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TP_COMMON_ID")
  private TemplateCommonEntity templateCommon;
}
