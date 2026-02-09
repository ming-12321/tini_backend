package com.tini.tiniprojectbackend.common.util;

import com.tini.tiniprojectbackend.user.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.access_expiration}")
  private Long accessExpiration;

  @Value("${jwt.refresh_expiration}")
  private Long refreshExpiration;

  // UserEntity 기반 토큰 생성
  public String generateToken(UserDTO user, String category) {
    return Jwts.builder()
        .setSubject(user.getUserId()) // 사용자 식별자
        .claim("userUuid", user.getUserUuid())
        .claim("category", category) // access or refresh
        .claim("name", user.getUserNick()) // 닉네임
        .claim("role", user.isAdminYN() ? "ADMIN" : "USER") // 권한
        .setExpiration(new Date(
            System.currentTimeMillis() +
                (category.equals("access") ? accessExpiration : refreshExpiration)))
        .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()) // .getBytes() 중요!
        .compact();
  }

  // 토큰에서 Claims 추출
  public Claims getClaimsFromToken(String token) {
    if (token == null || token.trim().isEmpty()) {
      log.error("token is empty");
      throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }

    try {
      return Jwts.parserBuilder() // 최신 방식
          .setSigningKey(secretKey.getBytes()) // 바이트 배열로 넘겨야 함
          .build()
          .parseClaimsJws(token.trim())
          .getBody();
    } catch (ExpiredJwtException e) {
      log.warn("만료된 토큰입니다.");
      return e.getClaims();
    } catch (Exception e) {
      log.error("토큰 파싱 중 예외 발생", e);
      throw e;
    }
  }

  // 토큰 만료 여부 확인
  public boolean isTokenExpired(String token) {
    return getClaimsFromToken(token).getExpiration().before(new Date());
  }

  //토큰에서 Uuid 추출
  public String getUserUuidFromToken(String token){
    return getClaimsFromToken(token).get("userUuid", String.class);
  }

  // 토큰에서 사용자 ID 추출
  public String getUseridFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  // 토큰에서 권한 추출
  public String getAuthorityFromToken(String token) {
    return getClaimsFromToken(token).get("role", String.class);
  }

  // 토큰에서 카테고리 추출 (access or refresh)
  public String getCategoryFromToken(String token) {
    return getClaimsFromToken(token).get("category", String.class);
  }

  // 만료시간 getter
  public Long getAccessExpiration() {
    return accessExpiration;
  }
  public Long getRefreshExpiration() {
    return refreshExpiration;
  }

}
