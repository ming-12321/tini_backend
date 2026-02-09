package com.tini.tiniprojectbackend.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  protected LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  protected LocalDateTime updatedAt;

}
