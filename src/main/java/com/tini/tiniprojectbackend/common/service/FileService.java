package com.tini.tiniprojectbackend.common.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.tini.tiniprojectbackend.common.dto.FileDTO;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

  private final SshClient sshClient;

  public List<FileDTO> sftpLs(String lsDir) {
    // lsDir 이 null 일때 사용할 home path 조회
    String sftpHomePath = sshClient.sftpHomePath();

    // sftp ls
    Vector<LsEntry> lsEntries = sshClient.sftpLs(lsDir);

    // vector dto 변환
    return lsEntries.stream().filter(lsEntry -> !(Objects.equals(lsEntry.getFilename(), ".") ||
        Objects.equals( lsEntry.getFilename(), ".."))).map(lsEntry -> FileDTO.builder()
        .filename(lsEntry.getFilename())
        .filePath(StringUtils.isNotBlank(lsDir) ? lsDir + "/" + lsEntry.getFilename() :
            sftpHomePath + "/" + lsEntry.getFilename())
        .fileSize(getFileSize(lsEntry.getAttrs().getSize()))
        .directoryYN(lsEntry.getLongname().startsWith("d"))
        .build()).collect(Collectors.toList());
  }

  private String getFileSize(Long fileSize) {
    long size = fileSize / 1024;
    if(size<1) {
      return fileSize + "B";
    } else if(size < 1000) {
      return fileSize / 1024 + "KB";
    } else if(1001<=size && size < 1000000) {
      return fileSize / 1024 / 1000 + "MB";
    } else {
      return fileSize / 1024 / 1000 / 1000 + "GB";
    }
  }

}
