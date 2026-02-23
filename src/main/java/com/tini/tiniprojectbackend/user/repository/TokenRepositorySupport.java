package com.tini.tiniprojectbackend.user.repository;

import com.tini.tiniprojectbackend.user.entity.TokenEntity;

public interface TokenRepositorySupport {

  TokenEntity getTokenByUuid(String userUuid);
}
