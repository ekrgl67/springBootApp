package com.springboot.transactional.service;

import com.springboot.cus.dao.CusCustomerDao;
import com.springboot.cus.domain.CusCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionalConstantService {

    private final CusCustomerDao cusCustomerDao;

    private Map<Long, CusCustomer> map = new LinkedHashMap<>();

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CusCustomer findByIdWithNewTransaction(Long id) {

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
}
