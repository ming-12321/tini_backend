package com.tini.tiniprojectbackend.diary.entity;

import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.enumeration.Position;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Entity(name = "TB_DIARY")
public class DiaryEntity {

  @Id
  @Column(name = "DIARY_ID")
  private int diaryId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "POSITION")
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

  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Builder(builderMethodName = "createDiaryBuilder", builderClassName = "createDiaryBuilder")
  public DiaryEntity(DiaryDTO diaryDTO, UserDTO userDTO){
    this.diaryId = diaryDTO.getDiaryId();
    this.name = diaryDTO.getName();
    this.position = diaryDTO.getPosition();
    this.rowIndex = diaryDTO.getRowIndex();
    this.calIndex = diaryDTO.getCalIndex();
    this.user = UserEntity.createUserBuilder().userDTO(userDTO).build();
    this.isMain = diaryDTO.getIsMain();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public void updateIsMain(int value) {
    this.isMain = value;
    this.updatedAt = LocalDateTime.now();
  }

  public void moveTo(Position position, int rowIndex, int calIndex) {
    this.position = position;
    this.rowIndex = rowIndex;
    this.calIndex = calIndex;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateDiary(DiaryDTO diaryDTO) {
    if (diaryDTO.getName() != null) {
      this.name = diaryDTO.getName();
    }
    this.updatedAt = LocalDateTime.now();
  }

}
