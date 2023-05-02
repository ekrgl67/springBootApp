package com.springboot.acc.dao;

import com.springboot.acc.domain.AccAccount;
import com.springboot.gen.enums.GenStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccAccountDao extends JpaRepository<AccAccount,Long> {

    List<AccAccount> findAllByStatusType(GenStatusType statusType);
}
