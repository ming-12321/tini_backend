package com.tini.tiniprojectbackend.diary.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.common.security.service.SessionService;
import com.tini.tiniprojectbackend.diary.dto.PageDTO;
import com.tini.tiniprojectbackend.diary.entity.PageEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import com.tini.tiniprojectbackend.diary.repository.PageRepository;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageService {

  private final SessionService sessionService;
  private final PageRepository pageRepository;

  /**
   * 페이지에 테마 적용 (속지, 표지, 책갈피)
   */
  public void savePage(PageDTO pageDTO) {

    UserDTO userDTO = sessionService.getUserFromToken();

    pageRepository.save(PageEntity.createPageBuilder().pageDTO(pageDTO).userDTO(userDTO).build());
  }

  /**
   * 페이지에 적용된 테마 수정 (속지, 표지, 책갈피)
   */
  @Transactional
  public void updatePage(PageDTO pageDTO) {

    PageEntity pageById = pageRepository.getPageById(pageDTO.getPageId());

    switch (pageDTO.getPageType()) {
      case BOOKMARK:
        pageById.updateBookmark(pageDTO.getBookmark());
        break;
      case INNER:
        pageById.updateInner(pageDTO.getInnerTheme());
        break;
      case COVER:
        pageById.updateCover(pageDTO.getCoverTheme());
        break;
    }
  }

  /**
   * 다이어리 페이지의 테마 조회 (속지, 표지, 책갈피)
   * @param pageId 테마 조회할 페이지 아이디
   * @return 테마를 조회한 페이지
   */
  public PageDTO getPageById(int pageId) {
    PageEntity pageById = pageRepository.getPageById(pageId);

    if (pageById == null) {
      throw new TiniException(TiniErrorCode.THEME_NOT_FOUND);
    } else {
      return PageDTO.from(pageById);
    }
  }

  /**
   * 다이어리에 적용된 모든 테마 조회 (속지, 표지, 책갈피)
   * @param diaryId 조회할 다이어리 아이디
   * @param pageType 페이지 타입 (속지, 표지, 책갈피)
   * @return 조회한 테마 리스트
   */
  public List<PageDTO> getPages(int diaryId, PageType pageType) {
    List<PageEntity> pageList = pageRepository.getPageList(diaryId, pageType);

    return pageList.stream().map(PageDTO::from).collect(Collectors.toList());
  }

  /**
   * 다이어리의 적용된 테마 삭제
   * @param pageId 테마를 삭제할 페이지 아이디
   */
  public void deletePage(int pageId) {
    PageEntity pageById = pageRepository.getPageById(pageId);

    if (pageById == null) {
      throw new TiniException(TiniErrorCode.THEME_NOT_FOUND);
    } else {
      pageRepository.deleteById(pageId);
    }
  }
}
