package com.springboot.acc.service;

import com.springboot.acc.converter.AccAccountMapper;
import com.springboot.acc.domain.AccMoneyTransfer;
import com.springboot.acc.dto.AccMoneyTransferDto;
import com.springboot.acc.dto.AccMoneyTransferSaveRequestDto;
import com.springboot.acc.enums.AccAccountActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccMoneyTransferService {

    private final AccMoneyTransferEntityService accMoneyTransferEntityService;
    private final AccAccountActivityService accAccountActivityService;

    public AccMoneyTransferDto transferMoney(AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto) {

        AccMoneyTransfer accMoneyTransfer = AccAccountMapper.INSTANCE.convertToAccMoneyTransfer(accMoneyTransferSaveRequestDto);
        accMoneyTransfer.setTransferDate(new Date());

        Long accountIdFrom = accMoneyTransfer.getAccountIdFrom();
        Long accountIdTo = accMoneyTransfer.getAccountIdTo();
        BigDecimal amount = accMoneyTransfer.getAmount();

        accAccountActivityService.moneyOut(accountIdFrom,amount, AccAccountActivityType.SEND);
        accAccountActivityService.moneyIn(accountIdTo,amount,AccAccountActivityType.GET);

        accMoneyTransfer = accMoneyTransferEntityService.save(accMoneyTransfer);
        AccMoneyTransferDto accMoneyTransferDto = AccAccountMapper.INSTANCE.convertToAccMoneyTransferDto(accMoneyTransfer);

        return accMoneyTransferDto;
    }
}
