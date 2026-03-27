package com.tini.tiniprojectbackend.user.dto;

import com.tini.tiniprojectbackend.user.entity.TokenEntity;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

  protected int tokenId;
  protected String refreshToken;
  protected String accessToken;
  protected Date refreshExpireAt;
  protected LocalDateTime revokeAt;
  protected String revokedReason;
  protected String deviceId;
  protected UserDTO user;

  @Builder(builderMethodName = "toTokenBuilder", builderClassName = "toTokenBuilder")
  public TokenDTO(TokenEntity tokenEntity) {
    this.tokenId = tokenEntity.getTokenId();
    this.refreshToken = tokenEntity.getRefreshToken();
    this.accessToken = tokenEntity.getAccessToken();
    this.refreshExpireAt = tokenEntity.getRefreshExpireAt();
    this.revokeAt = tokenEntity.getRevokeAt();
    this.revokedReason = tokenEntity.getRevokedReason();
    this.user = UserDTO.toUserBuilder().userEntity(tokenEntity.getUser()).build();
  }
}
