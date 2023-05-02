package com.springboot.gen.exceptions;

import com.springboot.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends GenBusinessException {

    public ItemNotFoundException(BaseErrorMessage baseErrorMassage) {
        super(baseErrorMassage);
    }
}
