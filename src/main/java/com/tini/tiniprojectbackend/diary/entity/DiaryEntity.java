package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.enumeration.Position;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "TB_DIARY")
public class DiaryEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DIARY_ID")
  private int diaryId;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "POSITION", nullable = false)
  @Enumerated(EnumType.STRING)
  private Position position;

  @Column(name = "ROW_INDEX")
  private int rowIndex;

  @Column(name = "CAL_INDEX")
  private int calIndex;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_UUID", nullable = false)
  private UserEntity user;

  @Column(name = "IS_MAIN", nullable = false)
  private int isMain;

  @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<IndexEntity>  indexEntities;

  @Builder(builderMethodName = "createDiaryBuilder", builderClassName = "createDiaryBuilder")
  public DiaryEntity(DiaryDTO diaryDTO, UserDTO userDTO) {
    this.name = diaryDTO.getName();
    this.position = diaryDTO.getPosition();
    this.rowIndex = diaryDTO.getRowIndex();
    this.calIndex = diaryDTO.getCalIndex();
    this.user = UserEntity.createUserBuilder().userDTO(userDTO).build();
    this.isMain = diaryDTO.getIsMain();
  }

  public void updateIsMain(int value) {
    this.isMain = value;
  }

  public void moveTo(Position position, int rowIndex, int calIndex) {
    this.position = position;
    this.rowIndex = rowIndex;
    this.calIndex = calIndex;
  }

  public void updateDiary(DiaryDTO diaryDTO) {
    if (diaryDTO.getName() != null) {
      this.name = diaryDTO.getName();
    }
  }

}