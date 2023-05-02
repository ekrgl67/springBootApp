package com.springboot.crd.dto;

import com.springboot.crd.enums.CrdCreditCardActivityType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CrdCreditCardActivityDto {

    private Long crdCreditCardId;
    private BigDecimal amount;
    private Date transactionDate;
    private String description;
    private CrdCreditCardActivityType cardActivityType;
}
