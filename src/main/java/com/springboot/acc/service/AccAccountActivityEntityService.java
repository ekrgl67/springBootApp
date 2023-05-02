package com.springboot.acc.service;

import com.springboot.acc.dao.AccAccountActivityDao;
import com.springboot.acc.domain.AccAccountActivity;
import com.springboot.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class AccAccountActivityEntityService extends BaseEntityService<AccAccountActivity, AccAccountActivityDao> {

    public AccAccountActivityEntityService(AccAccountActivityDao dao) {
        super(dao);
    }
}
