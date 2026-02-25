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
  protected Long id;

  @JsonProperty("connected_at")
  protected String connectedAt;

  @JsonProperty("properties")
  protected KakaoProperties properties;

  @JsonProperty("kakao_account")
  protected KakaoAccount kakaoAccount;


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class KakaoProperties {
    @JsonProperty("nickname")
    protected String nickName;

    @JsonProperty("profile_image")
    protected String profileImage;

    @JsonProperty("thumbnail_image")
    protected String thumbnailImage;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class KakaoAccount {
    @JsonProperty("email")
    protected String email;

    @JsonProperty("email_needs_agreement")
    protected Boolean emailNeedsAgreement;

    @JsonProperty("is_email_valid")
    protected Boolean isEmailValid;

    @JsonProperty("is_email_verified")
    protected Boolean isEmailVerified;

    @JsonProperty("age_range")
    protected String ageRange;

    @JsonProperty("birthyear")
    protected String birthYear;

    @JsonProperty("birthday")
    protected String birthDay;

    @JsonProperty("gender")
    protected String gender;
  }
}
