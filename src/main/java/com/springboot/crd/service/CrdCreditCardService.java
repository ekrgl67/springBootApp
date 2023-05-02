package com.springboot.crd.service;

import com.springboot.crd.converter.CrdCreditCardActivityMapper;
import com.springboot.crd.converter.CrdCreditCardMapper;
import com.springboot.crd.domain.CrdCreditCard;
import com.springboot.crd.domain.CrdCreditCardActivity;
import com.springboot.crd.dto.*;
import com.springboot.crd.enums.CrdCreditCardActivityType;
import com.springboot.crd.enums.CrdErrorMessage;
import com.springboot.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.springboot.crd.service.entityservice.CrdCreditCardEntityService;
import com.springboot.gen.enums.GenStatusType;
import com.springboot.gen.exceptions.GenBusinessException;
import com.springboot.gen.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrdCreditCardService {
    private final CrdCreditCardEntityService crdCreditCardEntityService;
    private final CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

    public List<CrdCreditCardResponseDto> findAll() {

        List<CrdCreditCard> crdCreditCardList = crdCreditCardEntityService.findAllByStatusType(GenStatusType.ACTIVE);
        List<CrdCreditCardResponseDto> result = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardResponseDtoList(crdCreditCardList);

        return result;
    }

    public CrdCreditCardResponseDto findById(Long id) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);
        CrdCreditCardResponseDto result = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardResponseDto(crdCreditCard);
        return result;
    }

    public CrdCreditCardResponseDto save(CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto) {

        Long cusCustomerId = crdCreditCardSaveRequestDto.getCusCustomerId();
        BigDecimal earning = crdCreditCardSaveRequestDto.getEarning();
        String cutoffDayStr = crdCreditCardSaveRequestDto.getCutoffDay();


        BigDecimal limit = calculateLimit(earning);
        LocalDate cutoffDateLocal = getCutoffDateLocal(cutoffDayStr);
        Date cutoffDate = DateUtil.convertToDate(cutoffDateLocal);

        Date dueDate = getDueDate(cutoffDateLocal);

        CrdCreditCard crdCreditCard = createCrdCreditCard(cusCustomerId, limit, cutoffDate, dueDate);

        CrdCreditCardResponseDto crdCreditCardResponseDto = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardResponseDto(crdCreditCard);

        return crdCreditCardResponseDto;
    }

    private CrdCreditCard createCrdCreditCard(Long cusCustomerId, BigDecimal limit, Date cutoffDate, Date dueDate) {
        Date expireDate = getExpireDate();
        Long cardNo = getCardNo();
        Long cvvNo = getCvvNo();

        CrdCreditCard crdCreditCard = new CrdCreditCard();
        crdCreditCard.setCusCustomerId(cusCustomerId);
        crdCreditCard.setCutoffDate(cutoffDate);
        crdCreditCard.setDueDate(dueDate);
        crdCreditCard.setExpireDate(expireDate);
        crdCreditCard.setCardNo(cardNo);
        crdCreditCard.setCvvNo(cvvNo);
        crdCreditCard.setTotalLimit(limit);
        crdCreditCard.setAvailableCardLimit(limit);
        crdCreditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        crdCreditCard.setCurrentDebt(BigDecimal.ZERO);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    private static Date getExpireDate() {
        LocalDate expireDateLocal = getExpireDateLocal();
        Date expireDate = DateUtil.convertToDate(expireDateLocal);
        return expireDate;
    }

    private static Date getDueDate(LocalDate cutoffDateLocal) {
        LocalDate dueDateLocal = getDueDateLocal(cutoffDateLocal);
        Date dueDate = DateUtil.convertToDate(dueDateLocal);
        return dueDate;
    }

    private static LocalDate getExpireDateLocal() { // 3 yıl sonrası
        return LocalDate.now().plusYears(3);
    }

    private static LocalDate getDueDateLocal(LocalDate cutoffDateLocal) {//10 gün sonrası
        return cutoffDateLocal.plusDays(10);
    }

    private static LocalDate getCutoffDateLocal(String cutoffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        Integer cutoffDay = getCutoffDay(cutoffDayStr);
        LocalDate cutoffDateLocal = LocalDate.of(currentYear, nextMonth, cutoffDay);
        return cutoffDateLocal;
    }

    private static BigDecimal calculateLimit(BigDecimal earning) {
        return earning.multiply(BigDecimal.valueOf(3));
    }

    private static Integer getCutoffDay(String cutoffDayStr) {
        if (!StringUtils.hasText(cutoffDayStr)) {
            cutoffDayStr = "1";
        }

        Integer cutoffDay = Integer.valueOf(cutoffDayStr);
        return cutoffDay;
    }

    private Long getCvvNo() {
        Long cvvNo = 123L;
        return cvvNo;
    }

    private Long getCardNo() {
        Long cardNo = getCvvNo();
        return cardNo;
    }

    public void cancel(Long id) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);

        crdCreditCard.setStatusType(GenStatusType.PASSIVE);
        crdCreditCard.setCancelDate(new Date());

        crdCreditCardEntityService.save(crdCreditCard);
    }

    public CrdCreditCardActivityDto spend(CrdCreditCardSpendDto crdCreditCardSpendDto) {

        BigDecimal amount = crdCreditCardSpendDto.getAmount();
        String description = crdCreditCardSpendDto.getDescription();

        CrdCreditCard crdCreditCard = getCrdCreditCard(crdCreditCardSpendDto);

        validateCreditCard(crdCreditCard);

        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().add(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().subtract(amount);

        validateCardLimit(currentAvailableLimit);
        updateCreditCardForSpend(crdCreditCard, currentDebt, currentAvailableLimit);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivityForSpend(amount, description, crdCreditCard);

        CrdCreditCardActivityDto result = CrdCreditCardActivityMapper.INSTANCE.convertTCrdCreditCardActivityDto(crdCreditCardActivity);
        return result;
    }

    private CrdCreditCard updateCreditCardForSpend(CrdCreditCard crdCreditCard, BigDecimal currentDebt, BigDecimal currentAvailableLimit) {
        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);

        return crdCreditCard;
    }

    private CrdCreditCard getCrdCreditCard(CrdCreditCardSpendDto crdCreditCardSpendDto) {
        Long cardNo = crdCreditCardSpendDto.getCardNo();
        Long cvvNo = crdCreditCardSpendDto.getCvvNo();
        Date expireDate = crdCreditCardSpendDto.getExpireDate();
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.findByCardNoAndCvvNoAndAndExpireDateAndStatusType(cardNo, cvvNo, expireDate, GenStatusType.ACTIVE);
        return crdCreditCard;
    }

    private static void validateCardLimit(BigDecimal currentAvailableLimit) {
        if (currentAvailableLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new GenBusinessException(CrdErrorMessage.INSUFFICIENT_CREDIT_CARD_LIMIT);
        }
    }

    private static void validateCreditCard(CrdCreditCard crdCreditCard) {
        if (crdCreditCard != null) {
            throw new GenBusinessException(CrdErrorMessage.INVALID_CREDIT_CARD);
        }
        if (crdCreditCard.getExpireDate().before(new Date())) {
            throw new GenBusinessException(CrdErrorMessage.CREDIT_CARD_EXPIRED);
        }
    }

    public CrdCreditCardActivity createCrdCreditCardActivityForSpend(BigDecimal amount, String description, CrdCreditCard crdCreditCard) {
        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.SPEND);
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setTransactionDate(new Date());
        crdCreditCardActivity.setDescription(description);

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    public CrdCreditCardActivityDto refund(Long id) {

        CrdCreditCardActivity oldCrdCreditCardActivity = crdCreditCardActivityEntityService.getByIdWithControl(id);
        BigDecimal amount = oldCrdCreditCardActivity.getAmount();

        CrdCreditCard crdCreditCard = updateCreditCardForRefund(oldCrdCreditCardActivity, amount);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardForRefurd(oldCrdCreditCardActivity, amount, crdCreditCard);

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        CrdCreditCardActivityDto result = CrdCreditCardActivityMapper.INSTANCE.convertTCrdCreditCardActivityDto(crdCreditCardActivity);

        return result;
    }

    private static CrdCreditCardActivity createCrdCreditCardForRefurd(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount, CrdCreditCard crdCreditCard) {
        String description = "Refund " + oldCrdCreditCardActivity.getDescription();

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.REFUND);
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setTransactionDate(new Date());
        crdCreditCardActivity.setDescription(description);
        return crdCreditCardActivity;
    }

    private CrdCreditCard updateCreditCardForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(oldCrdCreditCardActivity.getCrdCreditCardId());

        crdCreditCard = addLimitTocard(amount, crdCreditCard);

        return crdCreditCard;
    }

    private CrdCreditCard addLimitTocard(BigDecimal amount, CrdCreditCard crdCreditCard) {
        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().add(amount);

        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);
        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    public CrdCreditCardActivityDto payment(CrdCreditCardPaymentDto crdCreditCardPaymentDto) {

        Long crdCreditCardId = crdCreditCardPaymentDto.getCrdCreditCardId();
        BigDecimal amount = crdCreditCardPaymentDto.getAmount();

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(crdCreditCardId);

        addLimitTocard(amount, crdCreditCard);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardForPayment(amount, crdCreditCardId);
        CrdCreditCardActivityDto result = CrdCreditCardActivityMapper.INSTANCE.convertTCrdCreditCardActivityDto(crdCreditCardActivity);

        return result;

    }

    private static CrdCreditCardActivity createCrdCreditCardForPayment(BigDecimal amount, Long crdCreditCardId) {

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCardId);
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.REFUND);
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setTransactionDate(new Date());
        crdCreditCardActivity.setDescription("Payment ");
        return crdCreditCardActivity;
    }

    public List<CrdCreditCardActivityDto> findAllActivities(Long id, Date startDate, Date endDate, Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService.
                findAllByCrdCreditCardIdAndTransactionDateBetween(id, startDate, endDate);

        List<CrdCreditCardActivityDto> result = CrdCreditCardActivityMapper.INSTANCE.convertTCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        return result;
    }

    public CrdCreditCardDetails statement(Long id) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);
        Date termEndDate = crdCreditCard.getCutoffDate();
        Long crdCreditCardId = crdCreditCard.getId();

        LocalDate cutoffDateLocal = DateUtil.convertToLocalDate(termEndDate);

        LocalDate termStartDateLocal = cutoffDateLocal.minusMonths(1);
        Date termStartDate = DateUtil.convertToDate(termStartDateLocal);

        CrdCreditCardDetails creditCardDetailts = crdCreditCardEntityService.getCreditCardDetailts(crdCreditCardId);

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService.
                findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId, termStartDate, termEndDate);

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardActivityMapper.INSTANCE.convertTCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        creditCardDetailts.setCrdCreditCardActivityDtoList(crdCreditCardActivityDtoList);

        return creditCardDetailts;
    }
}
