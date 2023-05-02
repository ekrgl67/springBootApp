package com.springboot.acc.converter;

import com.springboot.acc.domain.AccAccount;
import com.springboot.acc.domain.AccAccountActivity;
import com.springboot.acc.domain.AccMoneyTransfer;
import com.springboot.acc.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccAccountMapper {

    AccAccountMapper INSTANCE = Mappers.getMapper(AccAccountMapper.class);

    AccAccountDto convertToAccAccountDto(AccAccount accAccount);

    List<AccAccountDto> convertToAccAccountDtoList(List<AccAccount> accAccountList);

    AccAccount convertToAccAccount(AccAccountSaveRequestDto accAccountSaveRequestDto);

    AccMoneyTransfer convertToAccMoneyTransfer(AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto);

    AccMoneyTransferDto convertToAccMoneyTransferDto(AccMoneyTransfer accMoneyTransfer);

    AccAccountActivityDto convertToAccAccountActivityDto(AccAccountActivity accAccountActivity);

}
