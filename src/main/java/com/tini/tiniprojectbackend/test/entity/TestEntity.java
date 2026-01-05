package com.tini.tiniprojectbackend.test.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("TEST")
public class TestEntity {
  @Id
  private String id;
  private String title;
}
