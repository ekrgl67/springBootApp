package com.springboot.acc.domain;

import com.springboot.acc.enums.AccMoneyTransferType;
import com.springboot.gen.domain.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACC_MONEY_TRANSFER")
@Data
public class AccMoneyTransfer extends BaseEntity {

    @Id
    @GeneratedValue(generator = "AccMoneyTransfer")
    @SequenceGenerator(name = "AccMoneyTransfer", sequenceName = "ACC_MONEY_TRANSFER_ID_SEQ")
    private Long id;

    @Column(name = "ID_ACC_ACCOUNT_FROM")
    private Long accountIdFrom;

    @Column(name = "ID_ACC_ACCOUNT_TO")
    private Long accountIdTo;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private Date transferDate;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AccMoneyTransferType transferType;
}