package com.tini.tiniprojectbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileDTO {

  protected String filename;
  protected String filePath;
  protected String fileSize;
  protected boolean directoryYN;

}
