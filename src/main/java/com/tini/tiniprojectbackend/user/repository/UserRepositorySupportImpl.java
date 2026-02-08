package com.tini.tiniprojectbackend.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositorySupportImpl implements UserRepositorySupport {

  private final JPAQueryFactory queryFactory;


  @Override
  public UserEntity getUserByUuid(String userUuid) {
//    return queryFactory
//        .selectFrom(userEntity)
//        .where(userEntity.userUuid.eq(userUuid))
//        .fetchOne();
    return null;
  }

  @Override
  public UserEntity getByUserId(String userId) {
//    return queryFactory
//        .selectFrom(userEntity)
//        .where(userEntity.userId.eq(userid))
//        .fetchOne();
    return null;
  }
}
