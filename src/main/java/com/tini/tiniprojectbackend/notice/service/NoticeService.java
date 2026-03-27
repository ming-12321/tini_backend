package com.tini.tiniprojectbackend.notice.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.notice.dto.NoticeDTO;
import com.tini.tiniprojectbackend.notice.entity.NoticeEntity;
import com.tini.tiniprojectbackend.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;

  /**
   * 공지사항 조회
   * @param noticeId 공지사항 아이디
   * @return 공지사항 정보
   */
  public NoticeDTO getNotice(int noticeId) {
    NoticeEntity notice = noticeRepository.getNotice(noticeId);

    if (notice == null) {
      throw new TiniException(TiniErrorCode.NOTICE_NOT_FOUND);
    }

    return NoticeDTO.builder()
        .noticeId(notice.getNoticeId())
        .title(notice.getTitle())
        .content(notice.getContent())
        .updatedAt(notice.getUpdatedAt())
        .createdAt(notice.getCreatedAt()).build();
  }

  /**
   * 공지사항 페이지 조회
   * @param search 검색어
   * @param pageable 페이지 정보
   * @return 공지사항 페이지
   */
  public Page<NoticeDTO> getNotice(String search, Pageable pageable) {

    Page<NoticeEntity> noticePage = noticeRepository.getNoticeList(search, pageable);

    return noticePage.map(notice -> NoticeDTO.builder()
        .noticeId(notice.getNoticeId())
        .title(notice.getTitle())
        .content(notice.getContent())
        .updatedAt(notice.getUpdatedAt())
        .createdAt(notice.getCreatedAt()).build());
  }

  /**
   * 공지사항 생성
   * @param noticeDTO
   */
  public void createNotice(NoticeDTO noticeDTO) {
    noticeRepository.save(NoticeEntity.builder()
        .title(noticeDTO.getTitle())
        .content(noticeDTO.getContent())
        .build());
  }

  public void deleteNotice(int noticeId) {
    NoticeEntity notice = noticeRepository.getNotice(noticeId);

    if (notice == null) {
      throw new TiniException(TiniErrorCode.NOTICE_NOT_FOUND);
    }

    noticeRepository.deleteById(noticeId);
  }

  @Transactional
  public void updateNotice(NoticeDTO noticeDTO) {
    NoticeEntity notice = noticeRepository.getNotice(noticeDTO.getNoticeId());

    if (notice == null) {
      throw new TiniException(TiniErrorCode.NOTICE_NOT_FOUND);
    }

    notice.updateTitle(noticeDTO.getTitle());
    notice.updateContent(noticeDTO.getContent());
  }
}
