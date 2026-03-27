package com.tini.tiniprojectbackend.user.repository;

import static com.tini.tiniprojectbackend.user.entity.QUserEntity.userEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.enumeration.SNS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositorySupportImpl implements UserRepositorySupport {

  private final JPAQueryFactory queryFactory;


  @Override
  public UserEntity getUserByUuid(String userUuid) {
    return queryFactory
        .selectFrom(userEntity)
        .where(userEntity.userUuid.eq(userUuid))
        .fetchOne();
  }

  @Override
  public UserEntity getUserByUserId(String userId, SNS sns) {
    return queryFactory
        .selectFrom(userEntity)
        .where(userEntity.userId.eq(userId), eqSns(sns))
        .fetchOne();
  }

  @Override
  public UserEntity getUserBySocialId(String socialId, SNS sns) {
    return queryFactory
        .selectFrom(userEntity)
        .where(userEntity.socialId.eq(socialId), eqSns(sns), userEntity.deletionYN.eq(false))
        .fetchOne();
  }

  private BooleanExpression eqSns(SNS sns) {return sns != null ? userEntity.sns.eq(sns) : null;}
}
