package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.CoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<CoverEntity, Integer>, CoverRepositorySupport {

}
