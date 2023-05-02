package com.springboot.cus.converter;

import com.springboot.cus.domain.CusCustomer;
import com.springboot.cus.dto.CusCustomerDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CusCustomerConverter {

    public List<CusCustomerDto> convertToCusCustomerDtoList(List<CusCustomer> cusCustomerList) {

        List<CusCustomerDto> cusCustomerDtoList = new ArrayList<>();
        for (CusCustomer cusCustomer : cusCustomerList) {

            CusCustomerDto cusCustomerDto = convertToCusCustomerDto(cusCustomer);

            cusCustomerDtoList.add(cusCustomerDto);
        }

        return cusCustomerDtoList;
    }

    public CusCustomerDto convertToCusCustomerDto(CusCustomer cusCustomer) {
        CusCustomerDto cusCustomerDto = new CusCustomerDto();

        cusCustomerDto.setId(cusCustomer.getId());
        cusCustomerDto.setName(cusCustomer.getName());
        cusCustomerDto.setSurname(cusCustomer.getSurname());
        cusCustomerDto.setIdentityNo(cusCustomer.getIdentityNo());
        return cusCustomerDto;
    }
}
