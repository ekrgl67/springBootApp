package com.springboot.crd.service.entityservice;

import com.springboot.crd.dao.CrdCreditCardDao;
import com.springboot.crd.domain.CrdCreditCard;
import com.springboot.crd.domain.CrdCreditCardActivity;
import com.springboot.crd.dto.CrdCreditCardDetails;
import com.springboot.gen.enums.GenStatusType;
import com.springboot.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CrdCreditCardEntityService extends BaseEntityService<CrdCreditCard, CrdCreditCardDao> {

    public CrdCreditCardEntityService(CrdCreditCardDao dao) {
        super(dao);
    }

    public List<CrdCreditCard> findAllByStatusType(GenStatusType statusType){
        return getDao().findAllByStatusType(statusType);
    }

    public CrdCreditCard findByCardNoAndCvvNoAndAndExpireDateAndStatusType(Long cardNo, Long cvvNo, Date expireDate, GenStatusType statusType){
        return getDao().findByCardNoAndCvvNoAndAndExpireDateAndStatusType(cardNo,cvvNo,expireDate,statusType);
    }

    public CrdCreditCardDetails getCreditCardDetailts(Long creditCardId){
        return getDao().getCreditCardDetails(creditCardId);
    }
}

