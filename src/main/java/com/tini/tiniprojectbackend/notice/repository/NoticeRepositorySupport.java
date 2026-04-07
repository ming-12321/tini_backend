package com.tini.tiniprojectbackend.notice.repository;

import com.tini.tiniprojectbackend.notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositorySupport {

  NoticeEntity getNotice(int noticeId);
  Page<NoticeEntity> getNoticeList(String search, Pageable pageable);

}
