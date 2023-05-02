package com.springboot.cus.service;

import com.springboot.cus.converter.CusCustomerConverter;
import com.springboot.cus.converter.CusCustomerMapper;
import com.springboot.cus.domain.CusCustomer;
import com.springboot.cus.dto.CusCustomerDto;
import com.springboot.cus.dto.CusCustomerSaveRequestDto;
import com.springboot.cus.dto.CusCustomerUpdateRequestDto;
import com.springboot.cus.enums.CusErrorMessage;
import com.springboot.gen.exceptions.GenBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CusCustomerService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private final CusCustomerConverter cusCustomerConverter;


    public List<CusCustomerDto> findAll() {
        List<CusCustomer> cusCustomerList = cusCustomerEntityService.findAll();

//        List<CusCustomerDto> cusCustomerDtoList = cusCustomerConverter.convertToCusCustomerDtoList(cusCustomerList);
        List<CusCustomerDto> cusCustomerDtoList = CusCustomerMapper.INSTANCE.cusCustomerListToCusCustomerDtoList(cusCustomerList);

        return cusCustomerDtoList;

    }

    public CusCustomerDto save(CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {

//        CusCustomer cusCustomer = cusCustomerConverter.convertToCusCustomer(cusCustomerSaveRequestDto);
//        cusCustomerEntityService.save(cusCustomer);

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.cusCustomerSaveRequestDtoToCusCustomer(cusCustomerSaveRequestDto);
        cusCustomerEntityService.save(cusCustomer);

        return cusCustomerConverter.convertToCusCustomerDto(cusCustomer);
    }

    public void delete(Long id) {
        CusCustomer byIdWithControl = cusCustomerEntityService.getByIdWithControl(id);
        cusCustomerEntityService.delete(byIdWithControl);
    }

    public CusCustomerDto findById(Long id) {

        CusCustomer byIdWithControl = cusCustomerEntityService.getByIdWithControl(id);
        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.cusCustomerToCusCustomerDto(byIdWithControl);
        return cusCustomerDto;
    }

    public CusCustomerDto update(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {

        CusCustomer cusCustomer;
        CusCustomer byIdWithControl = cusCustomerEntityService.getByIdWithControl(cusCustomerUpdateRequestDto.getId());
        if (byIdWithControl != null) {
            cusCustomer = CusCustomerMapper.INSTANCE.cusCustomerUpdateRequestDtoToCusCustomer(cusCustomerUpdateRequestDto);
            cusCustomerEntityService.save(cusCustomer);
        } else {
            throw new GenBusinessException(CusErrorMessage.CUSTOMER_ERROR_MESSAGE);
        }
        return cusCustomerConverter.convertToCusCustomerDto(cusCustomer);
    }
}