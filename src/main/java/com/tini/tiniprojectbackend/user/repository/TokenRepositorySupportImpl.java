package com.tini.tiniprojectbackend.user.repository;

import static com.tini.tiniprojectbackend.user.entity.QTokenEntity.tokenEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.user.entity.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositorySupportImpl implements TokenRepositorySupport {

  private final JPAQueryFactory queryFactory;

  @Override
  public TokenEntity getTokenByUuid(String userUuid) {
    return queryFactory.selectFrom(tokenEntity)
        .where(tokenEntity.user.userUuid.eq(userUuid))
        .fetchOne();
  }
}
