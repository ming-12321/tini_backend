package com.tini.tiniprojectbackend.diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InnerRepositorySupportImpl implements InnerRepositorySupport {

  private final JPAQueryFactory queryFactory;

}
