package com.tini.tiniprojectbackend;

import com.tini.tiniprojectbackend.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;

  @PostMapping("/")
  public ResponseEntity<String> saveDiary() {
    testService.save();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/")
  public String root() {
    return "Backend is running";
  }

  @GetMapping("/android")
  public String android() {
    return "Hello from Spring (Android)";
  }


}
