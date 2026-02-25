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
public class GoogleUserInfoDTO {

  @JsonProperty("id")
  private String id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("verified_email")
  private Boolean verifiedEmail;

  @JsonProperty("name")
  private String name;

  @JsonProperty("given_name")
  private String givenName;

  @JsonProperty("family_name")
  private String familyName;

  @JsonProperty("picture")
  private String picture;

  @JsonProperty("locale")
  private String locale;
}
