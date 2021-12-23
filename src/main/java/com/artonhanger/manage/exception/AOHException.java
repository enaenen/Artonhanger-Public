package com.artonhanger.manage.exception;


import com.artonhanger.manage.enums.ErrorEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

@Getter
public class AOHException extends RuntimeException {
    protected HttpStatus status;
    protected ErrorEnum errorEnum;

    public AOHException(ErrorEnum errorEnum) {
        super(errorEnum.toString());
        this.status = errorEnum.getErrorResponse().getHttpStatus();
        this.errorEnum = errorEnum;
    }

    public AOHException(ErrorEnum errorEnum, Throwable e) {
        super(errorEnum.toString(), e);
        this.status = errorEnum.getErrorResponse().getHttpStatus();
        this.errorEnum = errorEnum;
    }

    public AOHException(ErrorEnum errorEnum, List<Long> concatMessage) {
        super(errorEnum.toString());
        this.status = errorEnum.getErrorResponse().getHttpStatus();
        this.errorEnum = errorEnum;
        this.errorEnum.getErrorResponse().setMessage(concatMessage + " " + errorEnum.getMessage());
    }

    public AOHException(ErrorEnum errorEnum, String concatMessage) {
        super(errorEnum.toString());
        this.status = errorEnum.getErrorResponse().getHttpStatus();
        this.errorEnum = errorEnum;
        this.errorEnum.getErrorResponse().setMessage(concatMessage + " " + errorEnum.getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AOHException that = (AOHException) o;
        return getStatus() == that.getStatus() &&
                getErrorEnum() == that.getErrorEnum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getErrorEnum());
    }
}
