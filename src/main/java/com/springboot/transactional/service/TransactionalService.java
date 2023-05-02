package com.springboot.transactional.service;

import com.springboot.cus.domain.CusCustomer;
import com.springboot.cus.service.CusCustomerEntityService;
import com.springboot.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private final NonTransactionalService nonTransactionalService;
    private final TransactionalService2 transactionalService2;
    private final TransactionalConstantService transactionalConstantService;
    private final NonTransactionalConstantService nonTransactionalConstantService;

    public void save() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts2");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2N() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts3");

        cusCustomerEntityService.save(cusCustomer);

        nonTransactionalService.save();

        System.out.println("end");
    }

    public void saveT2T() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts5");

        cusCustomerEntityService.save(cusCustomer);

        save();

        System.out.println("end");
    }

    public void saveButError() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts6");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }


    public void saveT2RN() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts8-1");

        cusCustomerEntityService.save(cusCustomer);

        saveRN();

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRN() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts8-2");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2RNWithDifferentBean() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts9-1");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveRN();

        System.out.println("end");
    }

    public void saveT2RNButError() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts10");

        cusCustomerEntityService.save(cusCustomer);

        for (int i = 0; i < 10; i++) {
            try {
                transactionalService2.saveRN(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("end");
    }

    public void saveT2TButError() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts10");

        cusCustomerEntityService.save(cusCustomer);

        for (int i = 0; i < 10; i++) {
            try {
                transactionalService2.save(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMandatory() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts11-T");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2M() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts12-T");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveMandatory();

        System.out.println("end");
    }

    public void saveT2S() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts12-T");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveSupports();

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void doSomething() {

        for (int i = 0; i < 9999; i++) {
            CusCustomer cusCustomer = transactionalConstantService.findById(1L);
        }
    }

    public void saveNested() {

        transactionalService2.saveNested();

        System.out.println("end");
    }

    public void doSomethingWithNewTransaction() {

        for (int i = 0; i < 9999; i++) {
            CusCustomer cusCustomer = transactionalConstantService.findByIdWithNewTransaction(1L);
        }
    }

    public void saveT2Never() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts21-Non");

        cusCustomerEntityService.save(cusCustomer);

        nonTransactionalConstantService.saveNever();

        System.out.println("end");
    }
}
