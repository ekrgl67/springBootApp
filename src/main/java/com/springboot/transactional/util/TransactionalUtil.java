package com.springboot.transactional.util;

import com.springboot.cus.domain.CusCustomer;
import org.springframework.util.StringUtils;

public class TransactionalUtil {

    public static CusCustomer getDummyCusCustomer(String suffix) {

        String testName = "test";
        if (StringUtils.hasText(suffix)) {
            testName = testName + "-" + suffix;
        }

        CusCustomer cusCustomer = new CusCustomer();
        cusCustomer.setName(testName);
        cusCustomer.setSurname(testName);
        cusCustomer.setIdentityNo(12312312312L);
        cusCustomer.setPassword("123");

        return cusCustomer;
    }
}
