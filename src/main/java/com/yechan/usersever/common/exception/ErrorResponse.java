package com.yechan.usersever.common.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
    String message
) {

}
