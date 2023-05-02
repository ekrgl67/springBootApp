package com.springboot.acc.dao;

import com.springboot.acc.domain.AccAccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccAccountActivityDao extends JpaRepository<AccAccountActivity,Long> {

}
