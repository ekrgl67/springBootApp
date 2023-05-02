package com.springboot.acc.dto;

import com.springboot.acc.enums.AccAccountType;
import com.springboot.acc.enums.AccCurrencyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccAccountSaveRequestDto {

    private BigDecimal currentBalance;
    private AccCurrencyType currencyType;
    private AccAccountType accountType;
}
