package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer>, DiaryRepositorySupport {

}
