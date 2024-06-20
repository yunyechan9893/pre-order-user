package com.yechan.usersever.member.common.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class CommonResult {

    private boolean success;

    private int code;

    private String message;

    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;

    @JsonInclude(NON_EMPTY)
    private FieldError[] fieldErrors;

    public record FieldError(String field, String message) {

    }
}
