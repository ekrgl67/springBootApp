package com.springboot.crd.converter;

import com.springboot.crd.domain.CrdCreditCard;
import com.springboot.crd.dto.CrdCreditCardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrdCreditCardMapper {

    CrdCreditCardMapper INSTANCE = Mappers.getMapper(CrdCreditCardMapper.class);

    CrdCreditCardResponseDto convertToCrdCreditCardResponseDto(CrdCreditCard crdCreditCard);

    List<CrdCreditCardResponseDto> convertToCrdCreditCardResponseDtoList(List<CrdCreditCard> crdCreditCardList);

}
