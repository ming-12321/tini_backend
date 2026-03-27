package com.tini.tiniprojectbackend.notice.controller;

import com.tini.tiniprojectbackend.notice.dto.NoticeDTO;
import com.tini.tiniprojectbackend.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/tini/notice")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;

  @GetMapping("/{id}")
  public ResponseEntity<NoticeDTO> getNotice(@PathVariable int id) {
    return new ResponseEntity<>(noticeService.getNotice(id), HttpStatus.OK);
  }

  @GetMapping("/page")
  public ResponseEntity<Page<NoticeDTO>> getNoticePage(@RequestParam(required = false) String search,
      Pageable pageable) {
    return new ResponseEntity<>(noticeService.getNotice(search, pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<HttpStatus> createNotice(@RequestBody NoticeDTO notice) {
    noticeService.createNotice(notice);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteNotice(@PathVariable int id) {
    noticeService.deleteNotice(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/update")
  public ResponseEntity<HttpStatus> updateNotice(@RequestBody NoticeDTO notice) {
    noticeService.updateNotice(notice);
    return ResponseEntity.ok(HttpStatus.OK);
  }
}
