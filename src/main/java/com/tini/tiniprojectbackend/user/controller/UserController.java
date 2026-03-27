package com.tini.tiniprojectbackend.user.controller;

import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.user.dto.TokenDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.repository.UserRepository;
import com.tini.tiniprojectbackend.user.service.SocialService;
import com.tini.tiniprojectbackend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  private final JWTUtil jwtUtil;

  /**
   * 카카오 로그인 사용자 token 발급
   * @param tokenDTO 카카오 인가코드
   * @return
   */
  @PostMapping("/kakao/login")
  public ResponseEntity<TokenDTO> kakaoLogin(@RequestBody TokenDTO tokenDTO) {
    return new ResponseEntity<>( socialService.processKakaoLogin(tokenDTO),HttpStatus.OK);
  }

  /**
   *
   * @param tokenDTO
   * @return
   */
  @PostMapping("/google/login")
  public ResponseEntity<TokenDTO> googleLogin(@RequestBody TokenDTO tokenDTO) {
    return new ResponseEntity<>(socialService.processGoogleLogin(tokenDTO),HttpStatus.OK);
  }

  /**
   * 회원탈퇴
   * @param request HttpServletRequest (JWT에서 userUuid 추출)
   * @return 탈퇴 결과
   */
  @DeleteMapping("/withdraw")
  public ResponseEntity<String> withdraw(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    String accessToken = authorization.replace("Bearer ", "").trim();
    String userUuid = jwtUtil.getUserUuidFromToken(accessToken);

    socialService.withdrawUser(userUuid);
    return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
  }
}
