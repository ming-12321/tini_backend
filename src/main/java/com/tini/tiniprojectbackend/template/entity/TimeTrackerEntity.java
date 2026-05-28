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
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_TIME_TRACKER")
public class TimeTrackerEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "TITLE")
  private String title;
  @Column(name = "TARGET_START_TIME")
  private LocalDateTime targetStartTime;
  @Column(name = "TARGET_END_TIME")
  private LocalDateTime targetEndTime;
  @OneToMany(mappedBy = "timeTracker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<TimeTrackerItemEntity> timeTrackerItemEntities;
  @OneToMany(mappedBy = "timeTracker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<TemplateDiaryEntity> templateDiaryEntities;
}
