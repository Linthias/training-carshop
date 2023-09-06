package com.github.linthias.exceptions;

import com.github.linthias.dto.ErrorDto;

public class BaseException extends Exception {
    protected ErrorDto errorResponse;

    public BaseException(String message) {
        super(message);
        errorResponse = new ErrorDto(message, this.getClass().getName(), 500);
    }
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        errorResponse = new ErrorDto(message, cause.getClass().getName(), 500);
    }

    public ErrorDto getErrorResponse() {
        return errorResponse;
    }
}
