package com.springboot.acc.service;

import com.springboot.acc.converter.AccAccountMapper;
import com.springboot.acc.domain.AccAccount;
import com.springboot.acc.dto.AccAccountDto;
import com.springboot.acc.dto.AccAccountSaveRequestDto;
import com.springboot.gen.enums.GenStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccAccountService {

    private final AccAccountEntityService accAccountEntityService;

    public List<AccAccountDto> findAll() {
        List<AccAccount> accAccountList = accAccountEntityService.findAllByStatusType(GenStatusType.ACTIVE);

        List<AccAccountDto> accAccountDtoList = AccAccountMapper.INSTANCE.convertToAccAccountDtoList(accAccountList);

        return accAccountDtoList;

    }

    public AccAccountDto findById(Long id) {

        AccAccount byIdWithControl = accAccountEntityService.getByIdWithControl(id);
        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(byIdWithControl);
        return accAccountDto;
    }

    public AccAccountDto save(AccAccountSaveRequestDto accAccountSaveRequestDto) {

        //     String ibanNo = getIbanNo();
//        Long currentCustomerId = accAccountEntityService.getCurrentCustomerId();

        AccAccount accAccount = AccAccountMapper.INSTANCE.convertToAccAccount(accAccountSaveRequestDto);
        accAccount.setStatusType(GenStatusType.ACTIVE);
        //       accAccount.setIbanNo(ibanNo);
//        accAccount.setCusCustomerId(currentCustomerId);

        accAccountEntityService.save(accAccount);
        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(accAccount);
        return accAccountDto;
    }

    public void cancel(Long id) {
        AccAccount accAccount = accAccountEntityService.getByIdWithControl(id);
        accAccount.setStatusType(GenStatusType.PASSIVE);
        accAccount.setCancelDate(new Date());

        accAccountEntityService.save(accAccount);
    }

    private String getIbanNo() {
        String ibanNo = "TR12 1245 1245 1245 1245 124";
        return ibanNo;
    }
}
