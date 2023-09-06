package com.github.linthias.util;

import com.github.linthias.dto.ErrorDto;
import com.github.linthias.exceptions.BaseException;

public class ExceptionHandler {
    private static ExceptionHandler handlerInstance;

    private ExceptionHandler() {}

    public static ExceptionHandler getInstance() {
        if (handlerInstance == null) {
            handlerInstance = new ExceptionHandler();
        }

        return handlerInstance;
    }

    public ErrorDto handleException(Exception e) {
        ErrorDto errorDto;

        if (e instanceof BaseException) {
            errorDto = ((BaseException) e).getErrorResponse();
        } else {
            errorDto = new ErrorDto(e.getMessage(), e.getClass().getName(), 500);
        }

        return errorDto;
    }
}
