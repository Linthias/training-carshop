package com.github.linthias.exceptions;

import com.github.linthias.dto.ErrorDto;

public class BadRequestException extends  BaseException{
    public BadRequestException(String message) {
        super(message);
        errorResponse = new ErrorDto(message, this.getCause().toString(), 404);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        errorResponse = new ErrorDto(message, cause.toString(), 404);
    }
}
