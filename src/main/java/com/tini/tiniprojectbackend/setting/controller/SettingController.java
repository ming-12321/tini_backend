package com.tini.tiniprojectbackend.setting.controller;

import com.tini.tiniprojectbackend.setting.dto.SettingDTO;
import com.tini.tiniprojectbackend.setting.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tini/setting")
public class SettingController {

  private final SettingService settingService;

  /**
   * 설정 조회
   * @param settingDTO
   */
  @GetMapping
  public ResponseEntity<SettingDTO> getSetting(@RequestBody SettingDTO settingDTO) {
    return new ResponseEntity<>(settingService.getSetting(settingDTO.getUser().getUserUuid()), HttpStatus.OK);
  }

  /**
   * 앱 설정 업데이트
   * @param settingDTO 업데이트할 앱 설정
   */
  @PostMapping
  public ResponseEntity<HttpStatus> updateSetting(@RequestBody SettingDTO settingDTO) {
    settingService.updateSetting(settingDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
