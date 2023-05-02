package com.springboot.acc.domain;

import com.springboot.acc.enums.AccAccountActivityType;
import com.springboot.gen.domain.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACC_ACCOUNT_ACTIVITY")
@Data
public class AccAccountActivity extends BaseEntity {

    @GeneratedValue(generator = "AccAccountActivity")
    @SequenceGenerator(name = "AccAccountActivity", sequenceName = "ACC_ACCOUNT_ACTIVITY_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "ID_ACC_ACCOUNT")
    private Long accAccountId;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private Date transactionDate;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AccAccountActivityType accountActivityType;
}
