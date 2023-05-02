package com.springboot.cus.service;

import com.springboot.cus.dao.CusCustomerDao;
import com.springboot.cus.domain.CusCustomer;
import com.springboot.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class CusCustomerEntityService extends BaseEntityService<CusCustomer, CusCustomerDao> {

    public CusCustomerEntityService(CusCustomerDao dao) {
        super(dao);
    }

}
