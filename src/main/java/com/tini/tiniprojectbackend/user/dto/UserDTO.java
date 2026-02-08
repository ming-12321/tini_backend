package com.tini.tiniprojectbackend.user.dto;

import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.enumeration.Gender;
import com.tini.tiniprojectbackend.user.enumeration.SNS;
import com.tini.tiniprojectbackend.user.enumeration.Subscribe;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  protected String userUuid;
  protected String userId;
  protected String userNick;
  protected Gender userGender;
  protected LocalDateTime userBirthDate;
  protected SNS sns;
  protected String profile;
  protected String deviceUuid;
  protected boolean deletionYN;
  protected Subscribe appSubscribe;
  protected Subscribe  printSubscribe;
  protected int continuous;
  protected boolean adminYN;

  @Builder(builderMethodName = "toUserBuilder", builderClassName = "toUserBuilder")
  public UserDTO(UserEntity userEntity) {
    this.userUuid = userEntity.getUserUuid();
    this.userId = userEntity.getUserId();
    this.userNick = userEntity.getUserNick();
    this.userGender = userEntity.getUserGender();
    this.userBirthDate = userEntity.getUserBirthDate();
    this.sns = userEntity.getSns();
    this.profile = userEntity.getProfile();
    this.deviceUuid = userEntity.getDeviceUuid();
    this.deletionYN = userEntity.isDeletionYN();
    this.appSubscribe = userEntity.getAppSubscribe();
    this.printSubscribe = userEntity.getPrintSubscribe();
    this.continuous = userEntity.getContinuous();
    this.adminYN = userEntity.isAdminYN();
  }
}
