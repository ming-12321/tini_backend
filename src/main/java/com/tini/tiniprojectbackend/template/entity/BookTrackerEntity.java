package com.tini.tiniprojectbackend.template.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_BOOK_TRACKER")
public class BookTrackerEntity extends BaseEntity {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "TITLE")
  private String title;
  @Column(name = "TARGET_PAGE")
  private int targetPage;
  @Column(name = "SPACE_RATIO")
  private String spaceRatio;
  @Column(name = "SPACE_PAGE")
  private int spacePage;
  @Column(name = "DISPLAYED_ITEM")
  private String displayedItem;

}
