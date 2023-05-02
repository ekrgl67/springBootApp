package com.springboot.crd.service.entityservice;

import com.springboot.crd.dao.CrdCreditCardActivityDao;
import com.springboot.crd.domain.CrdCreditCardActivity;
import com.springboot.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CrdCreditCardActivityEntityService extends BaseEntityService<CrdCreditCardActivity, CrdCreditCardActivityDao> {

    public CrdCreditCardActivityEntityService(CrdCreditCardActivityDao dao) {
        super(dao);
    }

    public List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate){
        return getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId,startDate,endDate);
    }
}
