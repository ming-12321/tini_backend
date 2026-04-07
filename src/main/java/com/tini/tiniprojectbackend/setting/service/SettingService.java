package com.tini.tiniprojectbackend.setting.service;

import com.tini.tiniprojectbackend.setting.dto.SettingDTO;
import com.tini.tiniprojectbackend.setting.entity.SettingEntity;
import com.tini.tiniprojectbackend.setting.enumeration.CalTime;
import com.tini.tiniprojectbackend.setting.enumeration.DayTime;
import com.tini.tiniprojectbackend.setting.enumeration.Language;
import com.tini.tiniprojectbackend.setting.enumeration.Mode;
import com.tini.tiniprojectbackend.setting.enumeration.WeekTime;
import com.tini.tiniprojectbackend.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {

  private final SettingRepository settingRepository;

  public void createSetting(SettingDTO settingDTO) {
    settingRepository.save(SettingEntity.createSettingBuilder()
            .settingDTO(settingDTO)
        .build());
  }

  public SettingDTO getSetting(String userUuid) {
    SettingEntity settingEntity = settingRepository.getSettingByUserUUID(userUuid);
    if (settingEntity == null) {
      createSetting(SettingDTO.builder()
          .alertYN(true)
          .mode(Mode.LIGHT)
          .calTime(CalTime.EN)
          .dayTime(DayTime.XII)
          .language(Language.KO)
          .weekTime(WeekTime.EN)
          .build());

      SettingEntity settingByUserUUID = settingRepository.getSettingByUserUUID(userUuid);
      return SettingDTO.convert(settingByUserUUID, settingByUserUUID.getUser());
    } else {
      return SettingDTO.convert(settingEntity,settingEntity.getUser());
    }
  }

  public void updateSetting(SettingDTO settingDTO) {
    SettingEntity settingEntity = settingRepository.getSettingByUserUUID(
        settingDTO.getUser().getUserUuid());

    if (settingEntity == null) {
      createSetting(settingDTO);
    }  else {
      settingEntity.updateSetting(settingDTO);
    }

  }

  public void deleteSetting(String userUuid) {
    SettingEntity settingEntity = settingRepository.getSettingByUserUUID(userUuid);
    if (settingEntity != null) {
      settingRepository.delete(settingEntity);
    }
  }
}
