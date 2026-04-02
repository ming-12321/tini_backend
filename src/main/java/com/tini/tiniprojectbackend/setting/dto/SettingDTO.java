package com.tini.tiniprojectbackend.setting.dto;

import com.tini.tiniprojectbackend.diary.dto.DiaryDTO;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import com.tini.tiniprojectbackend.setting.entity.SettingEntity;
import com.tini.tiniprojectbackend.setting.enumeration.CalTime;
import com.tini.tiniprojectbackend.setting.enumeration.DayTime;
import com.tini.tiniprojectbackend.setting.enumeration.Language;
import com.tini.tiniprojectbackend.setting.enumeration.Mode;
import com.tini.tiniprojectbackend.setting.enumeration.WeekTime;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO.toUserBuilder;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SettingDTO {

  protected int settingId;
  protected Language language;
  protected CalTime calTime;
  protected WeekTime weekTime;
  protected DayTime dayTime;
  protected Mode mode;
  protected boolean alertYN;
  protected UserDTO user;

  public static SettingDTO convert(SettingEntity settingEntity, UserEntity userEntity) {
    return SettingDTO.builder()
        .settingId(settingEntity.getSettingId())
        .language(settingEntity.getLanguage())
        .calTime(settingEntity.getCalTime())
        .weekTime(settingEntity.getWeekTime())
        .dayTime(settingEntity.getDayTime())
        .mode(settingEntity.getMode())
        .alertYN(settingEntity.isAlertYN())
        .user(UserDTO.toUserBuilder().userEntity(userEntity).build())
        .build();
  }

}
