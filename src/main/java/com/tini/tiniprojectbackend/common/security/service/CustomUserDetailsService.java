package com.tini.tiniprojectbackend.common.security.service;

import com.tini.tiniprojectbackend.common.exception.TiniErrorCode;
import com.tini.tiniprojectbackend.common.exception.TiniException;
import com.tini.tiniprojectbackend.user.dto.UserDTO;
import com.tini.tiniprojectbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserDTO userDTO = userService.getUserByUserId(username);

    if (userDTO == null) {

      throw new TiniException(TiniErrorCode.USER_NOT_FOUND);
    }

    String role = (userDTO.isAdminYN()) ? "ADMIN" : "USER";

    return User.builder()
        .username(userDTO.getUserId())
        .roles(role)
        .build();
  }
}
