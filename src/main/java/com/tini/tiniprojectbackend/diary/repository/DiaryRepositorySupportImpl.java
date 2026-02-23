package com.tini.tiniprojectbackend.diary.repository;

import static com.tini.tiniprojectbackend.diary.entity.QDiaryEntity.diaryEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryRepositorySupportImpl implements DiaryRepositorySupport {

  private final JPAQueryFactory queryFactory;

  // 다이어리 단일 조회
  @Override
  public DiaryEntity getDiaryId(int diaryId){
    return queryFactory
        .selectFrom(diaryEntity)
        .where(diaryEntity.diaryId.eq(diaryId))
        .fetchOne();
  }

  // 사용자별 다이어리 목록 조회
  @Override
  public List<DiaryEntity> getDiariesByUserUuid(String userUuid){
    return queryFactory
        .selectFrom(diaryEntity)
        .where(diaryEntity.user.userUuid.eq(userUuid))
        .orderBy(diaryEntity.createdAt.desc())
        .fetch();
  }


}
