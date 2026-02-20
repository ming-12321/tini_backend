package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import java.util.List;

public interface DiaryRepositorySupport {

  DiaryEntity getDiaryId(int diaryId);

  List<DiaryEntity> getDiariesByUserUuid(String userUuid);


}
