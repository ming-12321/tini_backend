package com.tini.tiniprojectbackend.user.entity;

import com.tini.tiniprojectbackend.user.dto.TokenDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_TOKEN")
public class TokenEntity {

  @Id
  @Column(name = "TOKEN_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int tokenId;

  @Column(name = "ACCESS_TOKEN", length = 1000, nullable = false)
  private String accessToken;

  @Column(name = "REFRESH_TOKEN", length = 1000, nullable = false)
  private String refreshToken;

  @Column(name = "REFRESH_EXPIRE_AT")
  private Date refreshExpireAt;

  @Column(name = "REVOKED_AT")
  private LocalDateTime revokeAt;

  @Column(name = "REVOKED_REASON")
  private String revokedReason;

  @JoinColumn(name = "USER_UUID")
  @OneToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @Builder(builderMethodName = "createTokenBuilder", builderClassName = "createTokenBuilder")
  public TokenEntity(TokenDTO tokenDTO, UserDTO userDTO) {
    this.accessToken = tokenDTO.getAccessToken();
    this.refreshToken = tokenDTO.getRefreshToken();
    this.refreshExpireAt = tokenDTO.getRefreshExpireAt();
    this.user = UserEntity.createUserBuilder().userDTO(userDTO).build();
  }

  public void updateAccessToken(String accessToken) {this.accessToken = accessToken;}
  public void updateRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}
  public void updateRefreshExpireAt(Date refreshExpireAt) {this.refreshExpireAt = refreshExpireAt;}

  public void revokeToken(String reason) {
    this.revokeAt = LocalDateTime.now();
    this.revokedReason = reason;
    this.accessToken = "";
    this.refreshToken = "";
    this.refreshExpireAt = null;
  }
}
