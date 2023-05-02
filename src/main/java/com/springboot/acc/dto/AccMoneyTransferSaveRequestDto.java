package com.springboot.acc.dto;

import com.springboot.acc.enums.AccMoneyTransferType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccMoneyTransferSaveRequestDto {

    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private String description;
    private AccMoneyTransferType transferType;
}
