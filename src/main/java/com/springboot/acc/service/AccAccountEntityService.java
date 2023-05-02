package com.springboot.acc.service;

import com.springboot.acc.dao.AccAccountDao;
import com.springboot.acc.domain.AccAccount;
import com.springboot.gen.enums.GenStatusType;
import com.springboot.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccAccountEntityService extends BaseEntityService<AccAccount, AccAccountDao> {

    public AccAccountEntityService(AccAccountDao dao) {
        super(dao);
    }

    public List<AccAccount> findAllByStatusType(GenStatusType statusType){
        return getDao().findAllByStatusType(statusType);
    }
}
