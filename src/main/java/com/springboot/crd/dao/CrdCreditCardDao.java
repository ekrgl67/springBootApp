package com.springboot.crd.dao;

import com.springboot.crd.domain.CrdCreditCard;
import com.springboot.crd.dto.CrdCreditCardDetails;
import com.springboot.gen.enums.GenStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CrdCreditCardDao extends JpaRepository<CrdCreditCard, Long> {

    List<CrdCreditCard> findAllByStatusType(GenStatusType statusType);

    CrdCreditCard findByCardNoAndCvvNoAndAndExpireDateAndStatusType(Long cardNo, Long cvvNo, Date expireDate, GenStatusType statusType);


    @Query(
            " select " +
                    " new com.springboot.crd.dto.CrdCreditCardDetails( " +
                    " cusCustomer.name," +
                    " cusCustomer.surname," +
                    " crdCreditCard.cardNo," +
                    " crdCreditCard.expireDate," +
                    " crdCreditCard.currentDebt," +
                    " crdCreditCard.minimumPaymentAmount," +
                    " crdCreditCard.cutoffDate," +
                    " crdCreditCard.dueDate " + ") " +
                    " from CrdCreditCard  crdCreditCard " +
                    " left join CusCustomer cusCustomer on crdCreditCard.cusCustomerId = crdCreditCard.id" +
                    " where crdCreditCard.id = :creditCardId"
    )
    CrdCreditCardDetails getCreditCardDetails(Long creditCardId);
}
