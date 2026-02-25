package com.tini.tiniprojectbackend.diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.diary.entity.BookmarkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositorySupportImpl implements BookmarkRepositorySupport {

  private final JPAQueryFactory queryFactory;
}
