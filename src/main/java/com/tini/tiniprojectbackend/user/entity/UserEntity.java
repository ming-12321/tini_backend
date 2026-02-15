package com.tini.tiniprojectbackend.user.entity;

import com.tini.tiniprojectbackend.common.config.BooleanToInteger;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.enumeration.Gender;
import com.tini.tiniprojectbackend.user.enumeration.SNS;
import com.tini.tiniprojectbackend.user.enumeration.Subscribe;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_USER")
public class UserEntity {

  @Id
  @Column(name = "USER_UUID")
  private String userUuid;

  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "USER_NICK", length = 20)
  private String userNick;

  @Column(name = "USER_GENDER", length = 10)
  @Enumerated(EnumType.STRING)
  private Gender userGender;

  @Column(name = "USER_BIRTH_DATE")
  private LocalDateTime userBirthDate;

  @Column(name = "SNS")
  @Enumerated(EnumType.STRING)
  private SNS sns;

  @Column(name = "PROFILE", length = 200)
  private String profile;

  @Column(name = "DEVICE_UUID", length = 1000)
  private String deviceUuid;

  @Column(name = "DELETION_YN")
  @Convert(converter = BooleanToInteger.class)
  private boolean deletionYN;

  @Column(name = "APP_SUBSCRIBE")
  @Enumerated(EnumType.STRING)
  private Subscribe appSubscribe;

  @Column(name = "PRINT_SUBSCRIBE")
  @Enumerated(EnumType.STRING)
  private Subscribe  printSubscribe;

  @Column(name = "CONTINIOUS")
  private int continuous;

  @Column(name = "ADMIN_YN")
  @Convert(converter = BooleanToInteger.class)
  private boolean adminYN;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TOKEN_ID")
  private TokenEntity token;

  @Builder(builderMethodName = "createUserBuilder", builderClassName = "createUserBuilder")
  public UserEntity(UserDTO userDTO) {
    this.userUuid = userDTO.getUserUuid();
    this.userId = userDTO.getUserId();
    this.userNick = userDTO.getUserNick();
    this.userGender = userDTO.getUserGender();
    this.userBirthDate = userDTO.getUserBirthDate();
    this.sns = userDTO.getSns();
    this.profile = userDTO.getProfile() != null ? userDTO.getProfile() : null;
    this.deviceUuid = userDTO.getDeviceUuid();
    this.deletionYN = userDTO.isDeletionYN();
    this.appSubscribe = userDTO.getAppSubscribe();
    this.printSubscribe = userDTO.getPrintSubscribe();
    this.continuous = userDTO.getContinuous();
    this.adminYN = userDTO.isAdminYN();
  }
}
