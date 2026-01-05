package com.tini.tiniprojectbackend.test.service;

import com.tini.tiniprojectbackend.test.entity.TestEntity;
import com.tini.tiniprojectbackend.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

  private final TestRepository testRepository;

  public void save (){
    testRepository.save(TestEntity.builder().id("123").title("2026 다이어리").build());
  }
}
