package com.tini.tiniprojectbackend.user.repository;

import com.tini.tiniprojectbackend.user.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, String>, TokenRepositorySupport {

}
