package com.github.linthias.exceptions;

import com.github.linthias.dto.ErrorDto;

public class DtoNotValidException extends BaseException {
    public DtoNotValidException(String message) {
        super(message);
        errorResponse = new ErrorDto(message, this.getCause().toString(), 404);
    }

    public DtoNotValidException(String message, Throwable cause) {
        super(message, cause);
        errorResponse = new ErrorDto(message, cause.toString(), 404);
    }
}
