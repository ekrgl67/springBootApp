package com.springboot.crd.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CrdCreditCardSaveRequestDto {

    @NotNull
    private Long cusCustomerId;

    @NotNull
    private BigDecimal earning;

    private String cutoffDay;
}
