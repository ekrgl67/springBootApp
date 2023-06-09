package com.springboot.crd.domain;

import com.springboot.crd.enums.CrdCreditCardActivityType;
import com.springboot.gen.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CRD_CREDIT_CARD_ACTIVITY")
@Getter
@Setter
public class CrdCreditCardActivity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CrdCreditCardActivity", sequenceName = "CRD_CREDIT_CARD_ACTIVTY_ID_SEQ")
    @GeneratedValue(generator = "CrdCreditCardActivity")
    private Long id;

    @Column(name = "ID_CRD_CREDIT_CARD", nullable = false)
    private Long crdCreditCardId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private CrdCreditCardActivityType cardActivityType;

}

