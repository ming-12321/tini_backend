package com.tini.tiniprojectbackend.notice.repository;

import com.tini.tiniprojectbackend.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer>, NoticeRepositorySupport {

}
