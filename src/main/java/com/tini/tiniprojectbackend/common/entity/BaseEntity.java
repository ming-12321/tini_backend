package com.tini.tiniprojectbackend.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
class BaseEntity {

  @CreatedDate
  @Column(name = "CREATED_AT", updatable = false)
  protected LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "UPDATED_AT")
  protected LocalDateTime updatedAt;

}
