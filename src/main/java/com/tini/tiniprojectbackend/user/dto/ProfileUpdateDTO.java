package com.tini.tiniprojectbackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {

  private String nickname;
  private String birthdate; // yyyy-MM-dd
  private String gender; // FEMALE, MALE, NONBINARY (선택 안함이면 null)
}
