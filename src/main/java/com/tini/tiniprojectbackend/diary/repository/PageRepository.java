package com.tini.tiniprojectbackend.diary.repository;

import com.tini.tiniprojectbackend.diary.entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<PageEntity, Integer>, PageRepositorySupport {

}
