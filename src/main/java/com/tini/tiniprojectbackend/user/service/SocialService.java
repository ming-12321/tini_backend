package com.tini.tiniprojectbackend.user.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.common.util.JWTUtil;
import com.tini.tiniprojectbackend.setting.entity.SettingEntity;
import com.tini.tiniprojectbackend.setting.enumeration.CalTime;
import com.tini.tiniprojectbackend.setting.enumeration.DayTime;
import com.tini.tiniprojectbackend.setting.enumeration.Language;
import com.tini.tiniprojectbackend.setting.enumeration.Mode;
import com.tini.tiniprojectbackend.setting.enumeration.WeekTime;
import com.tini.tiniprojectbackend.setting.repository.SettingRepository;
import com.tini.tiniprojectbackend.user.dto.GoogleUserInfoDTO;
import com.tini.tiniprojectbackend.user.dto.KakaoUserInfoDTO;
import com.tini.tiniprojectbackend.user.dto.TokenDTO;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.entity.TokenEntity;
import com.tini.tiniprojectbackend.user.entity.UserEntity;
import com.tini.tiniprojectbackend.user.enumeration.Gender;
import com.tini.tiniprojectbackend.user.enumeration.SNS;
import com.tini.tiniprojectbackend.user.repository.TokenRepository;
import com.tini.tiniprojectbackend.user.repository.UserRepository;
import com.tini.tiniprojectbackend.user.util.GoogleOAuthClient;
import com.tini.tiniprojectbackend.user.util.KakaoOAuthClient;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {

  private final JWTUtil jwtUtil;
  private final KakaoOAuthClient kakaoOAuthClient;
  private final GoogleOAuthClient googleOAuthClient;
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final SettingRepository settingRepository;

  /**
   * 카카오 로그인 / 회원가입
   * @param tokenDTO
   * @return
   */
  @Transactional
  public TokenDTO processKakaoLogin(TokenDTO tokenDTO) {
    try {

      // 1. 인증 코드로 액세스 토큰 교환
//      String accessToken = kakaoOAuthClient.exchangeCodeForToken(code);

      // 2. 액세스 토큰으로 사용자 정보 조회
      KakaoUserInfoDTO kakaoUserInfo = kakaoOAuthClient.getUserInfo(tokenDTO.getAccessToken());

      if (kakaoUserInfo == null || kakaoUserInfo.getId() == null) {
        throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
      }

      // 소셜 사용자 email
      String socialUserId = String.valueOf(kakaoUserInfo.getKakaoAccount().getEmail());

      // 기존 사용자 확인
      UserEntity existingUser = userRepository.getUserByUserId(socialUserId, SNS.KAKAO);
      UserDTO userDTO;

      if (existingUser == null) {
        // 신규 사용자 회원가입
        registerKakaoUser(kakaoUserInfo);

        UserEntity userEntity = userRepository.getUserByUserId(socialUserId, SNS.KAKAO);

        userDTO = UserDTO.toUserBuilder()
            .userEntity(userEntity)
            .build();

      } else {
        // 기존 사용자 로그인
        userDTO = UserDTO.toUserBuilder()
            .userEntity(existingUser)
            .build();
      }

        // JWT 토큰 생성
        String jwtAccessToken = jwtUtil.generateToken(userDTO, "access");
        String jwtRefreshToken = jwtUtil.generateToken(userDTO, "refresh");

        // 리프레시 토큰 업데이트
        updateRefresh(userDTO.getUserUuid(), jwtRefreshToken);

        return TokenDTO.builder()
            .accessToken(jwtAccessToken)
            .refreshToken(jwtRefreshToken)
            .build();

    } catch (Exception e) {
      log.error("카카오 로그인 처리 중 오류 발생", e);
      throw new RuntimeException("카카오 로그인 처리 중 오류 발생");
    }
  }


  /**
   * 카카오 사용자 회원가입
   */
  public void registerKakaoUser(KakaoUserInfoDTO kakaoUserInfo) {
    try {
      log.info("카카오 사용자 회원가입 시작 - 소셜ID: {}", kakaoUserInfo.getKakaoAccount().getEmail());

      // UUID 생성
      String uuid = UUID.randomUUID().toString();

      UserEntity userEntity = UserEntity.builder()
          .userUuid(uuid)
          .userId(kakaoUserInfo.getKakaoAccount().getEmail())
          .userNick(kakaoUserInfo.getProperties().getNickName() != null ? kakaoUserInfo.getProperties().getNickName() : "카카오사용자")
          .userGender(changeUserGender(kakaoUserInfo.getKakaoAccount().getGender()))
          .userBirthDate(kakaoUserInfo.getKakaoAccount().getBirthDay() != null ? changeUserBirthday(kakaoUserInfo.getKakaoAccount().getBirthYear(), kakaoUserInfo.getKakaoAccount().getBirthDay()) : null)
          .sns(SNS.KAKAO)
          .profile(kakaoUserInfo.getProperties().getProfileImage())
          .continuous(0)
          .deletionYN(false)
          .adminYN(false)
          .build();

      // 사용자 정보 저장
      userRepository.save(userEntity);

      // 사용자 최초 설정 저장
      settingRepository.save(SettingEntity.builder()
              .user(userEntity)
              .mode(Mode.LIGHT)
              .alertYN(true)
              .calTime(CalTime.NU)
              .dayTime(DayTime.XII)
              .language(Language.KO)
              .weekTime(WeekTime.EN)
          .build());

      log.info("카카오 사용자 회원가입 완료 - 사용자ID: {}", userEntity.getUserId());

    } catch (Exception e) {
      log.error("카카오 사용자 회원가입 실패", e);
      throw new RuntimeException("카카오 사용자 회원가입 실패");
    }
  }

  /**
   * 구글 로그인 / 회원가입
   * @param tokenDTO
   * @return
   */
  @Transactional
  public TokenDTO processGoogleLogin(TokenDTO tokenDTO) {
    try {

      // 액세스 토큰으로 사용자 정보 조회
      GoogleUserInfoDTO googleUserInfo = googleOAuthClient.getUserInfo(tokenDTO.getAccessToken());

      if (googleUserInfo == null || googleUserInfo.getId() == null) {
        throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
      }

      // 소셜 사용자 email
      String socialUserId = googleUserInfo.getEmail();

      // 기존 사용자 확인
      UserEntity existingUser = userRepository.getUserByUserId(socialUserId, SNS.GOOGLE);
      UserDTO userDTO;

      if (existingUser == null) {
        // 신규 사용자 회원가입
        registerGoogleUser(googleUserInfo);

        UserEntity userEntity = userRepository.getUserByUserId(socialUserId, SNS.GOOGLE);

        userDTO = UserDTO.toUserBuilder()
            .userEntity(userEntity)
            .build();

      } else {
        // 기존 사용자 로그인
        userDTO = UserDTO.toUserBuilder()
            .userEntity(existingUser)
            .build();
      }

      // JWT 토큰 생성
      String jwtAccessToken = jwtUtil.generateToken(userDTO, "access");
      String jwtRefreshToken = jwtUtil.generateToken(userDTO, "refresh");

      // 리프레시 토큰 업데이트
      updateRefresh(userDTO.getUserUuid(), jwtRefreshToken);

      return TokenDTO.builder()
          .accessToken(jwtAccessToken)
          .refreshToken(jwtRefreshToken)
          .build();

    } catch (Exception e) {
      log.error("구글 로그인 처리 중 오류 발생", e);
      throw new RuntimeException("구글 로그인 처리 중 오류 발생");
    }
  }

  /**
   * 구글 사용자 회원가입
   * @param googleUserInfo
   */
  public void registerGoogleUser(GoogleUserInfoDTO googleUserInfo) {
    try {
      log.info("구글 사용자 회원가입 시작 - 소셜ID: {}", googleUserInfo.getEmail());

      // UUID 생성
      String uuid = UUID.randomUUID().toString();

      UserEntity userEntity = UserEntity.builder()
          .userUuid(uuid)
          .userId(googleUserInfo.getEmail())
          .userNick(googleUserInfo.getName() != null ? googleUserInfo.getName() : "구글사용자")
          .sns(SNS.GOOGLE)
          .profile(googleUserInfo.getPicture())
          .continuous(0)
          .deletionYN(false)
          .adminYN(false)
          .build();

      // 사용자 정보 저장
      userRepository.save(userEntity);

      // 사용자 최초 설정 저장
      settingRepository.save(SettingEntity.builder()
          .user(userEntity)
          .mode(Mode.LIGHT)
          .alertYN(true)
          .calTime(CalTime.NU)
          .dayTime(DayTime.XII)
          .language(Language.KO)
          .weekTime(WeekTime.EN)
          .build());

      log.info("구글 사용자 회원가입 완료 - 사용자ID: {}", userEntity.getUserId());
    } catch (Exception e) {
      log.error("구글 사용자 회원가입 실패", e);
      throw new RuntimeException("구글 사용자 회원가입 실패");
    }
  }

  /**
   * 업데이트 토큰
   * @param userUuid
   * @param refreshToken
   */
  @Transactional
  public void updateRefresh(String userUuid, String refreshToken) {

    TokenEntity tokenByUuid = tokenRepository.getTokenByUuid(userUuid);
    UserEntity userByUuid = userRepository.getUserByUuid(userUuid);

    if (tokenByUuid == null) {
      Claims claims = jwtUtil.getClaimsFromToken(refreshToken);
      tokenRepository.save(TokenEntity.builder()
              .refreshToken(refreshToken)
              .refreshExpireAt(claims.getExpiration())
              .user(userByUuid)
          .build());
    } else if ( !tokenByUuid.getRefreshToken().equals(refreshToken)) {
      if(StringUtils.isNotBlank(refreshToken)) {
        Claims claims = jwtUtil.getClaimsFromToken(refreshToken);
        tokenByUuid.updateRefreshExpireAt(claims.getExpiration());
      } else {
        tokenByUuid.updateRefreshExpireAt(null);
      }
      tokenByUuid.updateRefreshToken(refreshToken);
    }
  }

  /**
   * 소셜 계정 성별 변환 메서드
   * @param gender 문자열 형식 성별
   * @return 티니에서 사용하는 enum 타입 성별
   */
  public Gender changeUserGender(String gender) {
    if(StringUtils.isNotBlank(gender)) {
      return Gender.valueOf(gender.toUpperCase());
    }  else {
      return Gender.MALE;
    }
  }

  /**
   * 소셜 계정 생일 변환 메서드
   * @param birthYear 문자열 생일 연도
   * @param birthDay 문자열 생일 일자
   * @return 생년월일
   */
  public LocalDateTime changeUserBirthday(String birthYear, String birthDay) {
    if(StringUtils.isNotBlank(birthYear) && StringUtils.isNotBlank(birthDay)) {
      return LocalDateTime.parse(birthYear + "-" + birthDay);
    } else {
      return LocalDateTime.now();
    }
  }

}
