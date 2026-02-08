package com.tini.tiniprojectbackend.user.repository;

import com.tini.tiniprojectbackend.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, UserRepositorySupport {

}
