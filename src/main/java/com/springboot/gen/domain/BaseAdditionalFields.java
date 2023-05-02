package com.springboot.gen.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Embeddable
@Getter
@Setter
public class BaseAdditionalFields {

    @Column(nullable = false,updatable = false)
    @CreatedDate
    private Date createDate;

    @Column(nullable = false,updatable = false)
    @LastModifiedDate
    private Date updateDate;

    @Column()
    @CreatedBy
    private Long createdBy;

    @Column
    @LastModifiedBy
    private Long updatedBy;
}
