package com.springboot.gen.service;

import com.springboot.gen.domain.BaseAdditionalFields;
import com.springboot.gen.domain.BaseEntity;
import com.springboot.gen.enums.GenErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private final D dao;

    public D getDao() {
        return dao;
    }

    public List<E> findAll() {
        return dao.findAll();
    }

    public Optional<E> findById(Long id) {
        return dao.findById(id);
    }

    public E save(E entity) {

        setAdditionalFields(entity);
        return (E) dao.save(entity);
    }

    private void setAdditionalFields(E entity) {

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        Long currentCustomerId = getCurrentCustomerId();
        if (baseAdditionalFields == null) {
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getId() == null) {
            baseAdditionalFields.setCreateDate(new Date());
            baseAdditionalFields.setCreatedBy(currentCustomerId);//TODO jwt sonrası düzeltilecekk
        }
        baseAdditionalFields.setUpdateDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentCustomerId);
    }

    private Long getCurrentCustomerId() {
        return null;
    }

    public void delete(E entity) {
        dao.delete(entity);
    }

    public E getByIdWithControl(Long id) {
        Optional<E> entityOptional = findById(id);

        E entity;
        if (entityOptional.isPresent()) {
            entity = entityOptional.get();
        } else {
            throw new RuntimeException(GenErrorMessage.ITEM_NOT_FOUND.toString());
        }
        return entity;
    }

}
