package com.springboot.cus.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CusCustomerSaveRequestDto {

    private String name;
    private String surname;
    private Long identityNo;
    private String password;
}
