package com.tini.tiniprojectbackend.diary.repository;

import static com.tini.tiniprojectbackend.diary.entity.QPageEntity.pageEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.diary.entity.PageEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PageRepositorySupportImpl implements PageRepositorySupport {

  private final JPAQueryFactory queryFactory;


  @Override
  public PageEntity getPageById(int id) {
    return queryFactory.selectFrom(pageEntity)
        .where(pageEntity.pageId.eq(id))
        .fetchOne();
  }

  @Override
  public List<PageEntity> getPageList(int diaryId, PageType pageType) {
    return queryFactory.selectFrom(pageEntity)
        .where(pageEntity.diary.diaryId.eq(diaryId),eqPageType(pageType))
        .fetch();
  }

  BooleanExpression eqPageType(PageType pageType) {
    return switch (pageType) {
      case BOOKMARK -> pageEntity.pageType.eq(PageType.BOOKMARK);
      case INNER -> pageEntity.pageType.eq(PageType.INNER);
      case COVER -> pageEntity.pageType.eq(PageType.COVER);
    };
  }
}
