package com.springboot.acc.domain;

import com.springboot.acc.enums.AccAccountType;
import com.springboot.acc.enums.AccCurrencyType;
import com.springboot.gen.domain.BaseEntity;
import com.springboot.gen.enums.GenStatusType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "ACC_ACCOUNT")
@Data
public class AccAccount extends BaseEntity {

    @Id
    @SequenceGenerator(name = "AccAccount", sequenceName = "ACC_ACCOUNT_ID_SEQ")
    @GeneratedValue(generator = "AccAccount")
    private Long id;

    @Column(name = "ID_CUS_CUSTOMER ")
    private Long cusCustomerId;

    @Column(length = 30)
    private String ibanNo;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AccCurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 30)
    private AccAccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private GenStatusType statusType;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDate;
}
