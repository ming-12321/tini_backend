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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_TARGET_PAGE_TRACKER")
public class TargetPageEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "START_PAGE")
  private int startPage;
  @Column(name = "END_PAGE")
  private int endPage;
  @Column(name = "PAGE_COUNT")
  private int pageCount;
  @Column(name = "COLOR")
  private String color;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_TRACKER_ID")
  private BookTrackerEntity bookTracker;

}
