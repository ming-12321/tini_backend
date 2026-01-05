package com.tini.tiniprojectbackend.test.repository;

import com.tini.tiniprojectbackend.test.entity.TestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestEntity, String> {

}
