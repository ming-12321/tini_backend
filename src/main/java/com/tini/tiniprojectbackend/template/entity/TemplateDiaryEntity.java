package com.tini.tiniprojectbackend.template.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_TP_DIARY")
public class TemplateDiaryEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "DIARY_PAGE")
  private int diaryPage;
  @Column(name = "CUSTOM_TIME")
  private LocalDateTime customTime;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DIARY_ID")
  private DiaryEntity diary;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TP_COMMON_ID")
  private TemplateCommonEntity templateCommon;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TIME_TRACKER_ID")
  private TimeTrackerEntity timeTracker;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_TRACKER_ID")
  private BookTrackerEntity bookTracker;
}
