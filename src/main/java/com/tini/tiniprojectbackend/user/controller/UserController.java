package com.tini.tiniprojectbackend.user.controller;

import com.tini.tiniprojectbackend.user.dto.TokenDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.repository.UserRepository;
import com.tini.tiniprojectbackend.user.service.SocialService;
import com.tini.tiniprojectbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/tini/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final SocialService socialService;

  /**
   * 카카오 로그인 사용자 token 발급
   * @param tokenDTO 카카오 인가코드
   * @return
   */
  @PostMapping("/kakao/login")
  public ResponseEntity<TokenDTO> kakaoLogin(@RequestBody TokenDTO tokenDTO) {
    return new ResponseEntity<>( socialService.processKakaoLogin(tokenDTO),HttpStatus.OK);
  }
}
