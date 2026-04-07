package com.tini.tiniprojectbackend.setting.repository;

import com.tini.tiniprojectbackend.setting.entity.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<SettingEntity, Integer>, SettingRepositorySupport {

}
