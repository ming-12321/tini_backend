package com.tini.tiniprojectbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("connected_at")
  private String connectedAt;

  @JsonProperty("properties")
  private KakaoProperties properties;

  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class KakaoProperties {
    @JsonProperty("nickname")
    private String nickName;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("thumbnail_image")
    private String thumbnailImage;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class KakaoAccount {
    @JsonProperty("email")
    private String email;

    @JsonProperty("email_needs_agreement")
    private Boolean emailNeedsAgreement;

    @JsonProperty("is_email_valid")
    private Boolean isEmailValid;

    @JsonProperty("is_email_verified")
    private Boolean isEmailVerified;

    @JsonProperty("age_range")
    private String ageRange;

    @JsonProperty("birthyear")
    private String birthYear;

    @JsonProperty("birthday")
    private String birthDay;

    @JsonProperty("gender")
    private String gender;
  }
}
