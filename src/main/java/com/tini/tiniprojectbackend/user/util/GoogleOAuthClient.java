package com.tini.tiniprojectbackend.user.util;

import com.tini.tiniprojectbackend.user.dto.GoogleUserInfoDTO;
import java.util.Base64;
import java.util.Map;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuthClient {

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${social.google.tokeninfo-url:https://oauth2.googleapis.com/tokeninfo}")
  private String googleTokenInfoUrl;

  /**
   * 구글 ID 토큰(JWT)을 파싱하여 사용자 정보 조회
   * @param idToken 구글 ID 토큰 (JWT)
   * @return 구글 사용자 정보
   */
  public GoogleUserInfoDTO getUserInfo(String idToken) {
    try {
      // JWT payload (두 번째 파트) 추출
      String[] parts = idToken.split("\\.");
      if (parts.length != 3) {
        throw new IllegalArgumentException("유효하지 않은 JWT 형식입니다.");
      }

      String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
      Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

      GoogleUserInfoDTO googleUserInfo = GoogleUserInfoDTO.builder()
          .id((String) claims.get("sub"))
          .email((String) claims.get("email"))
          .verifiedEmail((Boolean) claims.get("email_verified"))
          .name((String) claims.get("name"))
          .givenName((String) claims.get("given_name"))
          .familyName((String) claims.get("family_name"))
          .picture((String) claims.get("picture"))
          .locale((String) claims.get("locale"))
          .build();

      log.info("구글 사용자 정보 파싱 성공");
      return googleUserInfo;

    } catch (Exception e) {
      log.error("구글 ID 토큰 파싱 실패: {}", e.getMessage());
      throw new RuntimeException("구글 ID 토큰 파싱에 실패했습니다.", e);
    }
  }

  /**
   * 구글 토큰 유효성 검증
   * @param accessToken 구글 액세스 토큰
   * @return 유효성 여부
   */
  public boolean validateToken(String accessToken) {
    try {
      String url = googleTokenInfoUrl + "?access_token=" + accessToken;
      ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

      log.info("구글 토큰 유효성 검증 성공");
      return response.getStatusCode().is2xxSuccessful();

    } catch (Exception e) {
      log.error("구글 토큰 유효성 검증 실패: {}", e.getMessage());
      return false;
    }
  }
}
