package com.tini.tiniprojectbackend.diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tini.tiniprojectbackend.diary.entity.ThemeEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ThemeRepositorySupportImpl implements ThemeRepositorySupport {

  private final JPAQueryFactory queryFactory;

  @Override
  public ThemeEntity getThemeById(int themeId) {
    return null;
  }

  @Override
  public List<ThemeEntity> getThemeList(PageType pageType) {
    return List.of();
  }
}
