package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.PageEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import java.util.List;

public interface PageRepositorySupport {

  PageEntity getPageById(int id);

  List<PageEntity> getPageList(int diaryId, PageType pageType);
}
