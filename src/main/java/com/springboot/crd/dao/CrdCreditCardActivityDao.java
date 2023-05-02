package com.springboot.crd.dao;

import com.springboot.crd.domain.CrdCreditCardActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CrdCreditCardActivityDao extends JpaRepository<CrdCreditCardActivity,Long> {

    List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate);
}
