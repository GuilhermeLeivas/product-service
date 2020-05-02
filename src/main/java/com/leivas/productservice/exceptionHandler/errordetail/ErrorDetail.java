package com.leivas.productservice.exceptionHandler.errordetail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class ErrorDetail extends RuntimeException {

    private LocalDate timestamp;
    private String error;
    private String message;
    private HttpStatus status;

    public ErrorDetail(LocalDate timestamp, String error, String message, HttpStatus status) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
        this.status = status;
    }
}
