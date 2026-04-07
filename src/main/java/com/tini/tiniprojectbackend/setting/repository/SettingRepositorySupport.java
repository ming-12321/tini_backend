package com.tini.tiniprojectbackend.setting.repository;

import com.tini.tiniprojectbackend.setting.entity.SettingEntity;

public interface SettingRepositorySupport {

  SettingEntity getSettingByUserUUID(String uuid);

}
