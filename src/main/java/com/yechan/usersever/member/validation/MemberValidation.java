package com.yechan.usersever.member.validation;

import static com.yechan.usersever.member.common.exception.MemberErrorCode.EMPTY_MEMBER;

import com.yechan.usersever.member.common.exception.MemberErrorCode;
import com.yechan.usersever.member.common.exception.MemberException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberValidation {

    public static void duplicateMemberId(Long id) {
        if (Objects.nonNull(id)) {
            throw new MemberException(MemberErrorCode.DUPLICATE_MEMBER);
        }
    }

    public static void checkMember(String encryptionId, boolean isPassword) {
        if (Boolean.FALSE.equals(isPassword) || Objects.isNull(encryptionId)) {
            throw new MemberException(EMPTY_MEMBER);
        }
    }
}
