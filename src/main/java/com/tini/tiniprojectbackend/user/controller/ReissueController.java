package com.tini.tiniprojectbackend.user.controller;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.user.dto.TokenDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.TokenEntity;
import com.tini.tiniprojectbackend.user.repository.TokenRepository;
import com.tini.tiniprojectbackend.user.service.SocialService;
import com.tini.tiniprojectbackend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/tini")
@RequiredArgsConstructor
public class ReissueController {

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final SocialService socialService;
  private final TokenRepository tokenRepository;

  /**
   * Access Token 재발급 + Refresh Token 갱신 (sliding expiration)
   * - Access Token 만료 + Refresh Token 유효 시 호출
   * - Refresh Token도 새로 발급하여 활동 중인 사용자의 로그인을 유지
   */
  @PostMapping("/reissue")
  public ResponseEntity<TokenDTO> reissue(HttpServletRequest request) {
    String accessToken = request.getHeader("Authorization");
    String refreshToken = request.getHeader("RefreshToken");

    // refresh token 유효성 검증
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new TiniException(TiniErrorCode.TOKEN_INVALID);
    }

    // refresh token이 만료되었으면 재발급 불가
    if (jwtUtil.isTokenExpired(refreshToken)) {
      throw new TiniException(TiniErrorCode.TOKEN_EXPIRED);
    }

    // refresh token에서 카테고리 확인
    String category = jwtUtil.getCategoryFromToken(refreshToken);
    if (!"refresh".equals(category)) {
      throw new TiniException(TiniErrorCode.TOKEN_INVALID);
    }

    // 사용자 정보 조회
    String userUuid = jwtUtil.getUserUuidFromToken(refreshToken);
    UserDTO userDTO = userService.getUserByUuid(userUuid);
    if (userDTO == null) {
      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
    }

    // DB에 저장된 refresh token과 일치하는지 검증
    TokenEntity storedToken = tokenRepository.getTokenByUuid(userUuid);
    if (storedToken == null || !refreshToken.equals(storedToken.getRefreshToken())) {
      log.warn("DB에 저장된 refresh token과 불일치 - userUuid: {}", userUuid);
      throw new TiniException(TiniErrorCode.TOKEN_INVALID);
    }

    // 새 access token + refresh token 발급 (sliding expiration)
    String newAccessToken = jwtUtil.generateToken(userDTO, "access");
    String newRefreshToken = jwtUtil.generateToken(userDTO, "refresh");

    // DB에 새 토큰 저장 (소셜 access token은 기존 것 유지)
    String socialAccessToken = socialService.getSocialAccessToken(userUuid);
    socialService.updateRefresh(userUuid,
        socialAccessToken != null ? socialAccessToken : "",
        newRefreshToken);

    log.info("토큰 재발급 완료 - userUuid: {}", userUuid);

    return ResponseEntity.ok(TokenDTO.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .build());
  }
}
