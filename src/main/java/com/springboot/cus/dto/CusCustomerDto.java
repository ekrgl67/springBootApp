package com.springboot.cus.dto;

import lombok.Data;

@Data
public class CusCustomerDto {

    private Long id;
    private String name;
    private String surname;
    private Long identityNo;
}
