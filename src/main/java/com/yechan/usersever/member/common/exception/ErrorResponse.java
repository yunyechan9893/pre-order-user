package com.yechan.usersever.member.common.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
    String message
) {

}
