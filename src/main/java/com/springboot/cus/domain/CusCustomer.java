package com.springboot.cus.domain;

import com.springboot.gen.domain.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "CUS_CUSTOMER")
@Data
public class CusCustomer extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CusCustomer", sequenceName = "CUS_CUSTOMER_ID_SEQ")
    @GeneratedValue(generator = "CusCustomer")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String surname;

    @Column(nullable = false)
    private Long identityNo;

    @Column(nullable = false)
    private String password;
}
