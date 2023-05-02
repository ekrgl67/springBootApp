package com.springboot.crd.domain;

import com.springboot.gen.domain.BaseEntity;
import com.springboot.gen.enums.GenStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CRD_CREDIT_CARD")
@Getter
@Setter
public class CrdCreditCard extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CrdCreditCard", sequenceName = "CRD_CREDIT_CARD_ID_SEQ")
    @GeneratedValue(generator = "CrdCreditCard")
    private Long id;

    @Column(nullable = false)
    private Long cusCustomerId;

    @Column(nullable = false)
    private Long cardNo;

    @Column(nullable = false)
    private Long cvvNo;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalLimit;

    @Column(precision = 19, scale = 2)
    private BigDecimal availableCardLimit;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentDebt;

    @Column(precision = 19, scale = 2)
    private BigDecimal minimumPaymentAmount;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date cutoffDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private GenStatusType statusType;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDate;
}
