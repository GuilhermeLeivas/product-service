package com.leivas.productservice.exceptionHandler;

import com.leivas.productservice.exceptionHandler.errordetail.ErrorDetail;
import com.leivas.productservice.exceptionHandler.exceptions.ErrorDuringProductUpdate;
import com.netflix.discovery.shared.transport.TransportException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ProductServiceExcepitonHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                  WebRequest request){

        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({TransportException.class})
    public ResponseEntity<?> handleTransportException(TransportException ex,WebRequest request) {

        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler({ErrorDuringProductUpdate.class})
    public ResponseEntity<?> handleErrorDuringProductUpdate(ErrorDuringProductUpdate ex, WebRequest request) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .timestamp(LocalDate.now())
                .error("ErrorDuringProductUpdate")
                .message("Update error, try again")
                .status(BAD_REQUEST)
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(errorDetail);
    }



}
