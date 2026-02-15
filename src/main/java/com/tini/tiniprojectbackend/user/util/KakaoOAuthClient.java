package com.tini.tiniprojectbackend.user.util;

import com.tini.tiniprojectbackend.user.dto.KakaoUserInfoDTO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

  private final RestTemplate restTemplate;

  @Value("${social.kakao.api-url:https://kapi.kakao.com}")
  private String kakaoApiUrl;

//  @Value("${social.kakao.client-id}")
//  private String clientId;
//
//  @Value("${social.kakao.client-secret}")
//  private String clientSecret;
//
//  @Value("${social.kakao.redirect-uri}")
//  private String redirectUri;

//  /**
//   * 카카오 인증 코드로 액세스 토큰 교환
//   * @param code 카카오 인증 코드
//   * @return 카카오 액세스 토큰
//   */
//  public String exchangeCodeForToken(String code) {
//    try {
//      HttpHeaders headers = new HttpHeaders();
//      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//      body.add("grant_type", "authorization_code");
//      body.add("client_id", clientId);
//      body.add("client_secret", clientSecret);
//      body.add("code", code);
//      body.add("redirect_uri", redirectUri);
//
//      HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
//
//      String url = "https://kauth.kakao.com/oauth/token";
//      ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
//
//      Map<String, Object> responseBody = response.getBody();
//      String accessToken = (String) responseBody.get("access_token");
//
//      log.info("카카오 토큰 교환 성공");
//      return accessToken;
//
//    } catch (Exception e) {
//      log.error("카카오 토큰 교환 실패: {}", e.getMessage());
//      throw new RuntimeException("카카오 토큰 교환에 실패했습니다.", e);
//    }
//  }

  /**
   * 카카오 액세스 토큰으로 사용자 정보 조회
   * @param accessToken 카카오 액세스 토큰
   * @return 카카오 사용자 정보
   */
  public KakaoUserInfoDTO getUserInfo(String accessToken) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + accessToken);
      headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

      HttpEntity<String> entity = new HttpEntity<>(headers);

      String url = kakaoApiUrl + "/v2/user/me";
      ResponseEntity<KakaoUserInfoDTO> response = restTemplate.exchange(
          url, HttpMethod.GET, entity, KakaoUserInfoDTO.class);

      log.info("카카오 사용자 정보 조회 성공");
      return response.getBody();

    } catch (Exception e) {
      log.error("카카오 사용자 정보 조회 실패: {}", e.getMessage());
      throw new RuntimeException("카카오 사용자 정보 조회에 실패했습니다.", e);
    }
  }

  /**
   * 카카오 토큰 유효성 검증
   * @param accessToken 카카오 액세스 토큰
   * @return 유효성 여부
   */
  public boolean validateToken(String accessToken) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + accessToken);

      HttpEntity<String> entity = new HttpEntity<>(headers);

      String url = kakaoApiUrl + "/v1/user/access_token_info";
      ResponseEntity<Object> response = restTemplate.exchange(
          url, HttpMethod.GET, entity, Object.class);

      log.info("카카오 토큰 유효성 검증 성공");
      return response.getStatusCode().is2xxSuccessful();

    } catch (Exception e) {
      log.error("카카오 토큰 유효성 검증 실패: {}", e.getMessage());
      return false;
    }
  }

}
