package com.tini.tiniprojectbackend.common.service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import io.micrometer.common.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class SshClient {

  @Value("${ssh.username}")
  String userName;
  @Value("${ssh.password}")
  String password;
  @Value("${ssh.port}")
  int port;
  @Value("${ssh.hostname}")
  String hostName;

  public Map<String, Object> execJsch(String command) {

    // 1. JSch 객체를 생성한다.
    JSch jsch = new JSch();
    //userName, password 설정
    // getEnv();
    String result = null;

    Session session = null;

    Channel channel = null;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try {
      // 2. 세션 객체를 생성한다 (사용자 이름, 접속할 호스트, 포트를 인자로 준다.)
      session = jsch.getSession(userName, hostName, port);
      // 3. 패스워드를 설정한다.
      session.setPassword(password);
      // 4. 세션과 관련된 정보를 설정한다.
      java.util.Properties config = new java.util.Properties();
      // 4-1. 호스트 정보를 검사하지 않는다.
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);

      // 5. 접속한다.
      session.connect();
      // 6. exec 채널을 연다.
      channel = session.openChannel("exec");
      // 7. 채널을 SSH용 채널 객체로 캐스팅한다
      ChannelExec channelExec = ((ChannelExec)channel);

      channelExec.setCommand(command);
      channel.setInputStream(null);
      // 8. 채널에 아웃풋설정을 한다.
      InputStream in = channel.getInputStream(); // channel.getInputStream();
      // 18.05.28 je.kim error stream 추가
      final InputStream err = channelExec.getErrStream();// <- 일반 에러 스트림
      channelExec.connect();
      result = getChannelOutput(channel, in, err);

    } catch (Exception e) {
      // 실패시 로그와 결과값 출력
      log.error("false : " + e.getMessage());
      log.error("false : " + e);
    } finally {
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
    }

    return null;
  }

  public String sftpSend(String remoteDir, MultipartFile file) {
    Map<String, Object> result = new HashMap<>();
    Session session = null;
    Channel channel = null;

    FileInputStream in = null;

    // 1. JSch 객체를 생성한다.
    JSch jsch = new JSch();
    //userName, password 설정
    // getEnv();

    try {
      // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
      session = jsch.getSession(userName, hostName, port);
      // 3. 패스워드를 설정한다.
      session.setPassword(password);

      // 4. 세션과 관련된 정보를 설정한다.
      java.util.Properties config = new java.util.Properties();
      // 4-1. 호스트 정보를 검사하지 않는다.
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      // 5. 접속한다.
      session.connect();

      // 6. sftp 채널을 연다.
      channel = session.openChannel("sftp");

      // 7. 채널에 연결한다.
      channel.connect();

      // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
      ChannelSftp channelSftp = (ChannelSftp)channel;

      // 8.1 해당 유저의 홈디렉토리
      String currentDirectory = channelSftp.pwd();

      // 8.2 '~/' -> /home/<username>/
      String destFolder = remoteDir.replaceFirst("^~", currentDirectory);

      // 8.3 폴더가 없으면 자동 생성
      // 업로드하려는 위치르 디렉토리를 변경한다.
      // 하위폴더까지 생성
      try {
        channelSftp.cd(destFolder);
      } catch (SftpException e) {
        String[] complPath = destFolder.split("/");
        channelSftp.cd("/");
        for (String dir : complPath) {
          if (dir.length() > 0) {
            try {
              channelSftp.cd(dir);
            } catch (SftpException e2) {
              channelSftp.mkdir(dir);
              channelSftp.cd(dir);
            }
          }
        }
        channelSftp.cd(destFolder);
      }

      InputStream inputStream = file.getInputStream();

      // 파일을 업로드한다.
      channelSftp.put(inputStream, file.getOriginalFilename());

      // 파일 저장 경로 리턴
      return destFolder;

    } catch (Exception e) {
      // 실패시 로그와 결과값 출력
      throw new IllegalArgumentException("파일 전송에 실패하였습니다. : " + e.getMessage());
    } finally {
      if (session != null) {
        session.disconnect();
      }
    }
  }

  public String sftpSend(String uploadPath, String uploadFileContent) {
    Session session = null;
    Channel channel = null;

    FileInputStream in = null;

    // 1. JSch 객체를 생성한다.
    JSch jsch = new JSch();
    //userName, password 설정
    // getEnv();

    try {
      // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
      session = jsch.getSession("root", hostName, port);
      // 3. 패스워드를 설정한다.
      session.setPassword("xii1@bPa$$");

      // 4. 세션과 관련된 정보를 설정한다.
      java.util.Properties config = new java.util.Properties();
      // 4-1. 호스트 정보를 검사하지 않는다.
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      // 5. 접속한다.
      session.connect();

      // 6. sftp 채널을 연다.
      channel = session.openChannel("sftp");

      // 7. 채널에 연결한다.
      channel.connect();

      // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
      ChannelSftp channelSftp = (ChannelSftp)channel;

      // 8.1 해당 유저의 홈디렉토리
      String currentDirectory = channelSftp.pwd();

      // 8.2 '~/' -> /home/<username>/

      String destFolder = uploadPath.replaceFirst("^~", currentDirectory);

      // 8.3 폴더가 없으면 자동 생성
      // 업로드하려는 위치르 디렉토리를 변경한다.
      // 하위폴더까지 생성
      try {
        channelSftp.cd(destFolder);
      } catch (SftpException e) {
        String[] complPath = destFolder.split("/");
        channelSftp.cd("/");
        for (String dir : complPath) {
          if (dir.length() > 0) {
            try {
              channelSftp.cd(dir);
            } catch (SftpException e2) {
              channelSftp.mkdir(dir);
              channelSftp.cd(dir);
            }
          }
        }
        channelSftp.cd(destFolder);
      }
      InputStream inputStream = new ByteArrayInputStream(uploadFileContent.getBytes(
          StandardCharsets.UTF_8));
      // 파일을 업로드한다.
      channelSftp.put(inputStream, "docker-compose.yaml");

      // 파일 저장 경로 리턴
      return uploadPath;

    } catch (Exception e) {
      // 실패시 로그와 결과값 출력
      throw new IllegalArgumentException("파일 생성에 실패하였습니다. : " + e.getMessage());
    } finally {
      if (session != null) {
        session.disconnect();
      }
    }
  }

  public String sftpHomePath() {
    Map<String, Object> result = new HashMap<>();
    Session session = null;
    Channel channel = null;

    FileInputStream in = null;
    java.util.Vector<ChannelSftp.LsEntry> list = new Vector<>();

    // 1. JSch 객체를 생성한다.
    JSch jsch = new JSch();
    //userName, password 설정
    // getEnv();

    try {
      // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
      session = jsch.getSession(userName, hostName, port);
      // 3. 패스워드를 설정한다.
      session.setPassword(password);

      // 4. 세션과 관련된 정보를 설정한다.
      java.util.Properties config = new java.util.Properties();
      // 4-1. 호스트 정보를 검사하지 않는다.
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      // 5. 접속한다.
      session.connect();

      // 6. sftp 채널을 연다.
      channel = session.openChannel("sftp");

      // 7. 채널에 연결한다.
      channel.connect();

      // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
      ChannelSftp channelSftp = (ChannelSftp)channel;

      // 8.1 해당 유저의 홈디렉토리
      String currentDirectory = channelSftp.pwd();

      // 파일 저장 경로 리턴
      return currentDirectory;

    } catch (Exception e) {
      // 실패시 로그와 결과값 출력
      throw new IllegalArgumentException("경로 조회를 실패하였습니다. : " + e.getMessage());
    } finally {
      if (session != null) {
        session.disconnect();
      }
    }
  }

  public java.util.Vector<ChannelSftp.LsEntry> sftpLs(String lsDir) {
    Map<String, Object> result = new HashMap<>();
    Session session = null;
    Channel channel = null;

    FileInputStream in = null;
    java.util.Vector<ChannelSftp.LsEntry> list = new Vector<>();

    // 1. JSch 객체를 생성한다.
    JSch jsch = new JSch();
    //userName, password 설정
    // getEnv();

    try {
      // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
      session = jsch.getSession(userName, hostName, port);
      // 3. 패스워드를 설정한다.
      session.setPassword(password);

      // 4. 세션과 관련된 정보를 설정한다.
      java.util.Properties config = new java.util.Properties();
      // 4-1. 호스트 정보를 검사하지 않는다.
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      // 5. 접속한다.
      session.connect();

      // 6. sftp 채널을 연다.
      channel = session.openChannel("sftp");

      // 7. 채널에 연결한다.
      channel.connect();

      // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
      ChannelSftp channelSftp = (ChannelSftp)channel;

      // 8.1 해당 유저의 홈디렉토리
      if (StringUtils.isNotBlank(lsDir)) {
        // 8.1 해당 유저의 홈디렉토리
        String currentDirectory = channelSftp.pwd();
        // 8.2 '~/' -> /home/<username>/
        String destFolder = lsDir.replaceFirst("^~", currentDirectory);
        channelSftp.cd(destFolder);
        list = channelSftp.ls(destFolder);
      } else {
        String currentDirectory = channelSftp.pwd();
        list = channelSftp.ls(currentDirectory);
      }

      // 파일 저장 경로 리턴
      return list;

    } catch (Exception e) {
      // 실패시 로그와 결과값 출력
      throw new IllegalArgumentException("경로 조회를 실패하였습니다. : " + e.getMessage());
    } finally {
      if (session != null) {
        session.disconnect();
      }
    }
  }

  private String getChannelOutput(Channel channel, InputStream in, InputStream err) throws IOException {

    byte[] buffer = new byte[1024];
    StringBuilder strBuilder = new StringBuilder();

    String line = "";
    while (true) {
      while (in.available() > 0) {
        int i = in.read(buffer, 0, 1024);
        if (i < 0) {
          break;
        }
        strBuilder.append(new String(buffer, 0, i));
      }

      while (err.available() > 0) {
        int i = err.read(buffer, 0, 1024);
        if (i < 0) {
          break;
        }
        strBuilder.append(new String(buffer, 0, i));
      }

      if (line.contains("logout")) {
        break;
      }

      if (channel.isClosed()) {
        break;
      }
      try {
        Thread.sleep(100);
      } catch (Exception ee) {
      }
    }

    return strBuilder.toString();
  }

  public Map<String, Object> sshTest() {
    Map<String, Object> result = new HashMap<>();

    Session session = null;
    JSch jSch = new JSch();
    //userName, password 설정
    // getEnv();

    try {
      // 2. 세션 객체를 생성한다 (사용자 이름, 접속할 호스트, 포트를 인자로 준다.)
      session = jSch.getSession(userName, hostName);
      // 3. 패스워드를 설정한다.
      session.setPassword(password);
      // 4. 세션과 관련된 정보를 설정한다.
      java.util.Properties config = new java.util.Properties();
      // 4-1. 호스트 정보를 검사하지 않는다.
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      // 5. 접속한다.
      session.connect();

    } catch (JSchException e) {
      // 실패시 로그와 결과값 출력
      result.put("status", false);
      result.put("log", e.toString() + "TTT");
      return result;
    } finally {
      if (session != null) {
        session.disconnect();
      }
    }

    result.put("status", true);
    result.put("log", "");

    return result;
  }
}
