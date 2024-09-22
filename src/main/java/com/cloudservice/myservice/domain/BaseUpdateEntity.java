package com.cloudservice.myservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUpdateEntity extends BaseEntity{

    @LastModifiedBy
    @Column(nullable = false)
    private Long modifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedTime;
}
