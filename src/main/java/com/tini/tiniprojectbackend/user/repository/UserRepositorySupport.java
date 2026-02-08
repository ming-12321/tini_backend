package com.tini.tiniprojectbackend.user.repository;

import com.tini.tiniprojectbackend.user.entity.UserEntity;

public interface UserRepositorySupport {

  UserEntity getUserByUuid(String userUuid);
  UserEntity getByUserId(String userId);

}
