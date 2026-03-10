package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.IndexEntity;
import java.util.List;

public interface IndexRepositorySupport {

  List<IndexEntity> getIndexList(int diaryId);

}
