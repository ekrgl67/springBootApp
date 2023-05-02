package com.springboot.cus.dao;

import com.springboot.cus.domain.CusCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CusCustomerDao extends JpaRepository<CusCustomer, Long> {

}
