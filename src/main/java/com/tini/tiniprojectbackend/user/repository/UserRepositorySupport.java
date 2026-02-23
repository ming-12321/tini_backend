package com.tini.tiniprojectbackend.user.repository;

import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.enumeration.SNS;

public interface UserRepositorySupport {

  UserEntity getUserByUuid(String userUuid);
  UserEntity getUserByUserId(String userId, SNS sns);
}
