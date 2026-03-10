package com.tini.tiniprojectbackend.diary.repository;

import static com.tini.tiniprojectbackend.diary.entity.QIndexEntity.indexEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.diary.entity.IndexEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IndexRepositorySupportImpl implements IndexRepositorySupport{

  private final JPAQueryFactory queryFactory;


  @Override
  public List<IndexEntity> getIndexList(int diaryId) {
    return queryFactory.selectFrom(indexEntity)
        .where(indexEntity.diary.diaryId.eq(diaryId))
        .fetch();
  }
}
