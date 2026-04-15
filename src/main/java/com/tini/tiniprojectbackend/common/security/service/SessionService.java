package com.tini.tiniprojectbackend.common.security.service;

import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserDTO getUserFromToken(){

    // RequestContextHolder를 통해 현재 요청의 HttpServletRequest 가져오기
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes == null) {
      log.error("HttpServletRequest를 가져올 수 없습니다.");
      return null;
    }
    String authorizationHeader = attributes.getRequest().getHeader("Authorization");

    try {
      //1단계. JWTUtil에서 Uuid 추출하기
      String userUuid = jwtUtil.getUserUuidFromToken(authorizationHeader.trim());
      //2단계. UserService로 사용자 정보 조회하기
      return userService.getUserByUuid(userUuid);
    } catch (Exception e) {
      log.error("토큰 분석 혹은 사용자 조회에 실패했습니다.", e);
      return null;
    }
  }
}
