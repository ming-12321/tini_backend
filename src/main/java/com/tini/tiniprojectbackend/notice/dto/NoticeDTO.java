package com.tini.tiniprojectbackend.notice.dto;

import com.tini.tiniprojectbackend.common.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO extends BaseDTO {

  protected int noticeId;
  protected String title;
  protected String content;


}
