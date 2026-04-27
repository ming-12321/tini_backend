package com.tini.tiniprojectbackend.common.controller;


import com.tini.tiniprojectbackend.common.dto.FileDTO;
import com.tini.tiniprojectbackend.common.service.FileService;
import com.tini.tiniprojectbackend.common.service.SshClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tini/file")
public class FileController {

  private final SshClient sshClient;
  private final FileService fileService;

  @PostMapping("/test")
  public ResponseEntity<Map<String, Object>> sshTest() {
    return new ResponseEntity<>(sshClient.sshTest(), HttpStatus.OK);
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(String remoteDir, MultipartFile file) {
    return new ResponseEntity<>(sshClient.sftpSend(remoteDir,file),HttpStatus.OK);
  }
}
