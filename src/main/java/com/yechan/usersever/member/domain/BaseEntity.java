package com.yechan.usersever.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Table
public class BaseEntity {

    @CreatedDate
    @Column(columnDefinition = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "modified_at")
    private LocalDateTime modifiedAt;

}
