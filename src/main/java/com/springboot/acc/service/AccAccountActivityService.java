package com.springboot.acc.service;

import com.springboot.acc.converter.AccAccountMapper;
import com.springboot.acc.domain.AccAccount;
import com.springboot.acc.domain.AccAccountActivity;
import com.springboot.acc.dto.AccAccountActivityDto;
import com.springboot.acc.dto.AccMoneyActivityRequestDto;
import com.springboot.acc.enums.AccAccountActivityType;
import com.springboot.acc.enums.AccErrorMessage;
import com.springboot.gen.exceptions.GenBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED )
public class AccAccountActivityService {

    private final AccAccountEntityService accAccountEntityService;
    private final AccAccountActivityEntityService accAccountActivityEntityService;

    public AccAccountActivityDto withDraw(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {

        Long accAccountId = accMoneyActivityRequestDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        AccAccountActivity accAccountActivity = moneyOut(accAccountId, amount, AccAccountActivityType.WITHDRAW);
        AccAccountActivityDto accAccountActivityDto = AccAccountMapper.INSTANCE.convertToAccAccountActivityDto(accAccountActivity);

        return accAccountActivityDto;
    }

    public AccAccountActivityDto depozit(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        Long accAccountId = accMoneyActivityRequestDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        AccAccountActivity accAccountActivity = moneyIn(accAccountId, amount, AccAccountActivityType.WITHDRAW);

        AccAccountActivityDto accAccountActivityDto = AccAccountMapper.INSTANCE.convertToAccAccountActivityDto(accAccountActivity);

        return accAccountActivityDto;
    }

    public AccAccountActivity moneyOut(Long accountId, BigDecimal amount, AccAccountActivityType accAccountActivityType) {
        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);

        BigDecimal newBalance = accAccount.getCurrentBalance().subtract(amount);
        validateBalance(newBalance);


        AccAccountActivity accAccountActivity = createAccAccountActivity(accountId, amount, newBalance, accAccountActivityType);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;

    }

    public AccAccountActivity moneyIn(Long accountId, BigDecimal amount, AccAccountActivityType accAccountActivityType) {
        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);

        BigDecimal newBalance = accAccount.getCurrentBalance().add(amount);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountId, amount, newBalance, accAccountActivityType);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;

    }

    private AccAccountActivity createAccAccountActivity(Long accountId, BigDecimal amount, BigDecimal newBalance, AccAccountActivityType accAccountActivityType) {
        AccAccountActivity accAccountActivity = new AccAccountActivity();
        accAccountActivity.setAccountActivityType(accAccountActivityType);
        accAccountActivity.setAccAccountId(accountId);
        accAccountActivity.setAmount(amount);
        accAccountActivity.setTransactionDate(new Date());
        accAccountActivity.setCurrentBalance(newBalance);

        accAccountActivity = accAccountActivityEntityService.save(accAccountActivity);
        return accAccountActivity;
    }

    private void updateCurrentBalance(AccAccount accAccount, BigDecimal newBalance) {
        accAccount.setCurrentBalance(newBalance);
        accAccount = accAccountEntityService.save(accAccount);
    }

    private static void validateBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new GenBusinessException(AccErrorMessage.INSUFFICIENT_BALANCE);
        }
    }
}
