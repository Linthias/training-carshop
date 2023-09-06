package com.github.linthias.dto;

public class ErrorDto {
    private String errorMessage;
    private String exceptionName;
    private Integer httpCode;

    public ErrorDto(String errorMessage, String exceptionName, Integer httpCode) {
        this.errorMessage = errorMessage;
        this.exceptionName = exceptionName;
        this.httpCode = httpCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public Integer getHttpCode() {
        return httpCode;
    }
}
