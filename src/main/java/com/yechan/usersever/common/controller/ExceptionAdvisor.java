package com.yechan.usersever.common.controller;

import com.yechan.usersever.common.exception.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice   // 전역 설정을 위한 annotaion
public class ExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> processValidationError(
        MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        List<ErrorResponse> errorResponses = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorResponses.add(
                ErrorResponse.of(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
                ));
        }

        return ResponseEntity.badRequest().body(errorResponses);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> solveHttpMessageNotReadableException(
        RuntimeException exception) {

        return ResponseEntity.badRequest().body(
            ErrorResponse.of(
                exception.getMessage()
            ));
    }

}
