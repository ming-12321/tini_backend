package com.tini.tiniprojectbackend.user.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.user.dto.ProfileUpdateDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.enumeration.Gender;
import com.tini.tiniprojectbackend.user.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  /**
   * 사용자 UUID로 사용자 정보 조회
   * @param userUuid 사용자 UUID
   * @return 사용자 정보
   */
  public UserDTO getUserByUuid(String userUuid) {
    UserEntity userEntity = userRepository.getUserByUuid(userUuid);

    if (userEntity == null) {
      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
    }

    return UserDTO.toUserBuilder()
        .userEntity(userEntity)
        .build();
  }


  /**
   * 아이다로 사용자 정보 조회
   * @param userId 사용자 아이디
   * @return 사용자 정보
   */
  public UserDTO getUserByUserId(String userId) {

    UserEntity userEntity = userRepository.getUserByUserId(userId, null);
    if (userEntity == null) {
      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
    }

    return UserDTO.toUserBuilder()
        .userEntity(userEntity)
        .build();
  }

  /**
   * 회원가입 후 프로필(닉네임/생년월일/성별) 확정
   * @param userUuid 사용자 UUID
   * @param profileUpdateDTO 프로필 입력값
   */
  @Transactional
  public void updateProfile(String userUuid, ProfileUpdateDTO profileUpdateDTO) {
    UserEntity userEntity = userRepository.getUserByUuid(userUuid);
    if (userEntity == null) {
      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
    }

    Gender gender = StringUtils.isNotBlank(profileUpdateDTO.getGender())
        ? Gender.valueOf(profileUpdateDTO.getGender().toUpperCase())
        : null;

    LocalDate birthDate = StringUtils.isNotBlank(profileUpdateDTO.getBirthdate())
        ? LocalDate.parse(profileUpdateDTO.getBirthdate(), DateTimeFormatter.ISO_LOCAL_DATE)
        : null;

    userEntity.updateProfile(
        profileUpdateDTO.getNickname(),
        gender,
        birthDate != null ? birthDate.atStartOfDay() : null
    );
  }

}
