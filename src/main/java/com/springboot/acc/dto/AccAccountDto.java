package com.springboot.acc.dto;

import com.springboot.acc.enums.AccAccountType;
import com.springboot.acc.enums.AccCurrencyType;
import com.springboot.gen.enums.GenStatusType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccAccountDto {

    private Long id;
    private Long cusCustomerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private AccCurrencyType currencyType;
    private AccAccountType accountType;
    private GenStatusType statusType;
}
