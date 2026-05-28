package com.tini.tiniprojectbackend.template.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_CAL_BUDGET_LIST")
public class CalBudgetListEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String category;
  private int amount;
  @Column(name = "TRANSACTION")
  private String transaction;
  @Column(name = "SATISFACTION")
  private int satisfaction;
}
