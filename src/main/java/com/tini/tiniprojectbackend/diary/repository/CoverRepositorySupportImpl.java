package com.tini.tiniprojectbackend.diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoverRepositorySupportImpl implements CoverRepositorySupport {

  private final JPAQueryFactory queryFactory;

}
