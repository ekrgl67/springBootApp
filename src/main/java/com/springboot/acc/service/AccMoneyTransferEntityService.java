package com.springboot.acc.service;

import com.springboot.acc.dao.AccMoneyTransferDao;
import com.springboot.acc.domain.AccMoneyTransfer;
import com.springboot.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class AccMoneyTransferEntityService extends BaseEntityService<AccMoneyTransfer, AccMoneyTransferDao> {

    public AccMoneyTransferEntityService(AccMoneyTransferDao dao) {
        super(dao);
    }
}
