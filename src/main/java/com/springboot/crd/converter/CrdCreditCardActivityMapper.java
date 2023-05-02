package com.springboot.crd.converter;

import com.springboot.crd.domain.CrdCreditCardActivity;
import com.springboot.crd.dto.CrdCreditCardActivityDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrdCreditCardActivityMapper {

    CrdCreditCardActivityMapper INSTANCE = Mappers.getMapper(CrdCreditCardActivityMapper.class);

    CrdCreditCardActivityDto convertTCrdCreditCardActivityDto(CrdCreditCardActivity crdCreditCardActivity);
    List<CrdCreditCardActivityDto> convertTCrdCreditCardActivityDtoList(List<CrdCreditCardActivity> crdCreditCardActivityList);

}
