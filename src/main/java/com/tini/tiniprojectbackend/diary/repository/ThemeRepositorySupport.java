package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.ThemeEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import java.util.List;

public interface ThemeRepositorySupport {

  ThemeEntity getThemeById(int themeId);

  List<ThemeEntity> getThemeList(PageType pageType);
}
