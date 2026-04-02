package com.tini.tiniprojectbackend.setting.entity;

import com.tini.tiniprojectbackend.common.entity.BaseEntity;
import com.tini.tiniprojectbackend.setting.dto.SettingDTO;
import com.tini.tiniprojectbackend.setting.enumeration.CalTime;
import com.tini.tiniprojectbackend.setting.enumeration.DayTime;
import com.tini.tiniprojectbackend.setting.enumeration.Language;
import com.tini.tiniprojectbackend.setting.enumeration.Mode;
import com.tini.tiniprojectbackend.setting.enumeration.WeekTime;
import com.tini.tiniprojectbackend.user.dto.TokenDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_SETTING")
public class SettingEntity extends BaseEntity {

  @Id
  @Column(name = "SETTING_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int settingId;

  @Column(name = "LANGUAGE")
  @Enumerated(EnumType.STRING)
  private Language language;

  @Column(name = "CAL_TIME")
  @Enumerated(EnumType.STRING)
  private CalTime calTime;

  @Column(name = "WEEK_TIME")
  @Enumerated(EnumType.STRING)
  private WeekTime weekTime;

  @Column(name = "DAY_TIME")
  @Enumerated(EnumType.STRING)
  private DayTime dayTime;

  @Column(name = "MODE")
  @Enumerated(EnumType.STRING)
  private Mode mode;

  @Column(name = "ALERT")
  private boolean alertYN;

  @OneToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @Builder(builderMethodName = "createSettingBuilder", builderClassName = "createSettingBuilder")
  public SettingEntity(SettingDTO settingDTO) {
    this.language = settingDTO.getLanguage();
    this.calTime = settingDTO.getCalTime();
    this.weekTime = settingDTO.getWeekTime();
    this.dayTime = settingDTO.getDayTime();
    this.mode = settingDTO.getMode();
    this.alertYN = settingDTO.isAlertYN();
    this.user = UserEntity.createUserBuilder().userDTO(settingDTO.getUser()).build();
  }

  public void updateSetting(SettingDTO settingDTO) {
    this.language = settingDTO.getLanguage();
    this.calTime = settingDTO.getCalTime();
    this.weekTime = settingDTO.getWeekTime();
    this.dayTime = settingDTO.getDayTime();
    this.mode = settingDTO.getMode();
    this.alertYN = settingDTO.isAlertYN();
  }
}
