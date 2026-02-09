package com.tini.tiniprojectbackend.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

  private int tokenId;
  private String refreshToken;
  private LocalDateTime refreshExpireAt;
  private LocalDateTime revokeAt;
  private String revokedReason;
  private UserDTO user;


}
