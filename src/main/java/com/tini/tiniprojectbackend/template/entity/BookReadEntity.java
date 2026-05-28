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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_BOOK_READ")
public class BookReadEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "STARTED_AT")
  private LocalDateTime startedAt;
  @Column(name = "ENDED_AT")
  private LocalDateTime endedAt;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_LIST_ID")
  private BookListEntity bookList;
}
