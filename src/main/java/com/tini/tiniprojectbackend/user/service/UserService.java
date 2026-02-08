package com.tini.tiniprojectbackend.user.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
//import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

//  private final JWTUtil jwtUtil;
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

    UserEntity userEntity = userRepository.getByUserId(userId);
    if (userEntity == null) {
      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
    }

    return UserDTO.toUserBuilder()
        .userEntity(userEntity)
        .build();
  }

//  /**
//   *
//   * @param userDTO
//   * @param refreshToken
//   */
//  @Transactional
//  public void updateRefresh(UserDTO userDTO, String refreshToken) {
//    UserEntity userEntity = userRepository.getUserByUuid(userDTO.getUserUuid());
//    if (userEntity == null) {
//      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
//    }
//    if ((userEntity.getUserRefreshToken() == null) || (!userEntity.getUserRefreshToken()
//        .equals(refreshToken))) {
//      userEntity.updateRefresh(refreshToken);
//    }
//  }

}
