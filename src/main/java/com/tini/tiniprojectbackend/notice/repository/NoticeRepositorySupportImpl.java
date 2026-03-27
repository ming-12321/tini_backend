package com.tini.tiniprojectbackend.notice.repository;

import static com.tini.tiniprojectbackend.notice.entity.QNoticeEntity.noticeEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.notice.entity.NoticeEntity;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositorySupportImpl implements NoticeRepositorySupport {

  private final JPAQueryFactory queryFactory;

  @Override
  public NoticeEntity getNotice(int noticeId) {
    return queryFactory.selectFrom(noticeEntity)
        .where(noticeEntity.noticeId.eq(noticeId))
        .fetchOne();
  }

  @Override
  public Page<NoticeEntity> getNoticeList(String search, Pageable pageable) {

    List<NoticeEntity> noticeList = queryFactory.selectFrom(noticeEntity)
        .where(containsTitle(search))
        .orderBy(noticeEntity.updatedAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = queryFactory.select(noticeEntity.count())
        .from(noticeEntity)
        .where(containsTitle(search))
        .fetchOne();


    return new PageImpl<>(noticeList, pageable, count);
  }

  BooleanExpression containsTitle(String search) {
    return StringUtils.isNotBlank(search) ? noticeEntity.title.contains(search) : null;
  }
}
