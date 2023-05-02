package com.springboot.gen.exceptions;

import com.springboot.gen.enums.BaseErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
@Data
public class GenBusinessException extends RuntimeException{

    private final BaseErrorMessage baseErrorMassage;
}
