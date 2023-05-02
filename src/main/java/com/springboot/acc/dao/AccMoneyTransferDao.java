package com.springboot.acc.dao;

import com.springboot.acc.domain.AccMoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccMoneyTransferDao extends JpaRepository<AccMoneyTransfer,Long> {
}
