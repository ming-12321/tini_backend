package com.tini.tiniprojectbackend.diary.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.diary.dto.ThemeDTO;
import com.tini.tiniprojectbackend.diary.entity.ThemeEntity;
import com.tini.tiniprojectbackend.diary.enumeration.PageType;
import com.tini.tiniprojectbackend.diary.repository.ThemeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeService {

  private final ThemeRepository themeRepository;

  /**
   * 테마 생성 (표지, 속지, 책갈피)
   */
  public void createTheme(ThemeDTO themeDTO) {
    themeRepository.save(ThemeEntity.createThemeBuilder().themeDTO(themeDTO).build());
  }


  /**
   * 테마 수정 (표지, 속지, 책갈피)
   */
  @Transactional
  public void updateTheme(ThemeDTO themeDTO) {
    ThemeEntity themeById = themeRepository.getThemeById(themeDTO.getThemeId());

    if (themeById == null) {
      throw new TiniException(TiniErrorCode.THEME_NOT_FOUND);
    } else {
      themeById.updateTheme(themeDTO);
    }
  }

  /**
   * 테마 조회 (표지, 속지, 책갈피)
   * @param themeId 조회할 테마 아이디
   */
  public ThemeDTO getThemeById(int themeId) {

    ThemeEntity themeById = themeRepository.getThemeById(themeId);

    if (themeById == null) {
      throw new TiniException(TiniErrorCode.THEME_NOT_FOUND);
    } else {
      return ThemeDTO.from(themeById);
    }

  }

  /**
   * 테마 리스트 조회 (표지, 속지, 책갈피)
   * @param pageType 표지, 속지, 책갈피
   */
  public List<ThemeDTO> getThemeList(PageType pageType) {

    List<ThemeEntity> themeList = themeRepository.getThemeList(pageType);

    return themeList.stream().map(ThemeDTO::from).collect(Collectors.toList());
  }

  /**
   * 테마 삭제 (표지, 속지, 책갈피)
   * @param themeId 삭제할 테마 아이디
   */
  public void deleteThemeById(int themeId) {

    ThemeEntity themeById = themeRepository.getThemeById(themeId);

    if (themeById == null) {
      throw new TiniException(TiniErrorCode.THEME_NOT_FOUND);
    } else {
      themeRepository.deleteById(themeId);
    }
  }
}
