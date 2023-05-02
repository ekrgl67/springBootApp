package com.springboot.transactional.service;

import com.springboot.cus.dao.CusCustomerDao;
import com.springboot.cus.domain.CusCustomer;
import com.springboot.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NonTransactionalConstantService {

    private final CusCustomerDao cusCustomerDao;

    private Map<Long, CusCustomer> map = new LinkedHashMap<>();

    public CusCustomer findById(Long id) {

        CusCustomer customer = map.get(id);
        if (customer != null) {
            return customer;
        }

        Optional<CusCustomer> customerOptional = cusCustomerDao.findById(id);

        CusCustomer cusCustomer;
        if (customerOptional.isPresent()) {
            cusCustomer = customerOptional.get();
        } else {
            throw new RuntimeException("Error");
        }

        map.put(cusCustomer.getId(), cusCustomer);

        return cusCustomer;
    }

    @Transactional(propagation = Propagation.NEVER)
    public void saveNever() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts20-N");

        cusCustomerDao.save(cusCustomer);

        System.out.println("end");
    }
}
