package com.tini.tiniprojectbackend.common.security.handler;

import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.enumeration.SNS;
import com.tini.tiniprojectbackend.user.service.SocialService;
import com.tini.tiniprojectbackend.user.service.UserService;
import com.tini.tiniprojectbackend.user.util.KakaoOAuthClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final SocialService socialService;
  private final KakaoOAuthClient kakaoOAuthClient;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      @Nullable Authentication authentication) {

    String authorization = request.getHeader("Authorization");

    if (authorization != null && !authorization.isEmpty()) {
      // Bearer 접두사 제거
      String accessToken = authorization.replace("Bearer ", "").trim();

      try {
        String userUuid = jwtUtil.getUserUuidFromToken(accessToken); //토큰에서 Uuid꺼냄

        if (userUuid != null) {
          UserDTO userDTO = userService.getUserByUuid(userUuid); //Uuid로 유저 DTO 조회

          log.info("소셜 로그인 사용자 로그아웃 처리 - 사용자: {}, 경로: {}", userUuid, userDTO.getSns());

          // 소셜 플랫폼에서 토큰 해제
          revokeSocialToken(userDTO);

          // 백엔드에서 refresh token 삭제
          socialService.updateRefresh(userUuid, "", "");

          // JSON 응답 형식으로 통일
          response.setStatus(HttpServletResponse.SC_OK);
          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
          response.setCharacterEncoding("UTF-8");
          response.getWriter().write("{\"message\":\"로그아웃 성공\",\"status\":\"success\"}");
        }
      } catch (Exception e) {
        log.error("로그아웃 처리 중 오류 발생", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
          response.getWriter().write("{\"message\":\"로그아웃 처리 중 오류 발생\",\"status\":\"error\"}");
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      try {
        response.getWriter().write("{\"message\":\"유효하지 않은 요청\",\"status\":\"error\"}");
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
  }


  // 소셜 플랫폼에서 토큰 해제
  private void revokeSocialToken(UserDTO userDTO) {
    try {
      String socialAccessToken = socialService.getSocialAccessToken(userDTO.getUserUuid());

      if (socialAccessToken == null || socialAccessToken.isBlank()) {
        log.warn("소셜 액세스토큰이 없습니다 - 사용자: {}", userDTO.getUserUuid());
        return;
      }

      if (userDTO.getSns() == SNS.KAKAO) {
        kakaoOAuthClient.logoutUser(socialAccessToken);
        log.info("카카오 토큰 해제 완료 - 사용자: {}", userDTO.getUserUuid());

      } else if (userDTO.getSns() == SNS.GOOGLE) {

      } else if (userDTO.getSns() == SNS.APPLE) {

      }
    } catch (Exception e) {
      log.error("소셜 토큰 해제 중 오류 발생", e);
      // 소셜 토큰 해제 실패해도 백엔드 로그아웃은 계속 진행
    }
  }
}
