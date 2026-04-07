package com.tini.tiniprojectbackend.setting.repository;

import static com.tini.tiniprojectbackend.setting.entity.QSettingEntity.settingEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.setting.entity.SettingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SettingRepositorySupportImpl implements SettingRepositorySupport {

  private final JPAQueryFactory queryFactory;


  @Override
  public SettingEntity getSettingByUserUUID(String uuid) {
    return queryFactory.selectFrom(settingEntity)
        .where(settingEntity.user.userUuid.eq(uuid))
        .fetchOne();
  }
}
