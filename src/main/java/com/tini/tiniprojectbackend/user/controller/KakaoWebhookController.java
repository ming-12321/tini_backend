package com.tini.tiniprojectbackend.user.controller;

import com.tini.tiniprojectbackend.user.service.SocialService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/tini/webhook/kakao")
@RequiredArgsConstructor
public class KakaoWebhookController {

  private final SocialService socialService;

  /**
   * 카카오 unlink webhook
   * 카카오에서 사용자가 연결 끊기를 했을 때 호출됨
   * @param payload webhook payload (user_id, referrer_type 등)
   * @return 처리 결과
   */
  @PostMapping("/unlink")
  public ResponseEntity<String> handleUnlink(@RequestBody Map<String, Object> payload) {
    log.info("카카오 unlink webhook 수신: {}", payload);

    Object userIdObj = payload.get("user_id");
    if (userIdObj == null) {
      log.warn("카카오 unlink webhook - user_id 없음");
      return ResponseEntity.badRequest().body("user_id is required");
    }

    String kakaoUserId = String.valueOf(userIdObj);
    socialService.handleKakaoUnlinkWebhook(kakaoUserId);

    return ResponseEntity.ok("success");
  }
}
