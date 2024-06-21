package com.yechan.usersever.member.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.yechan.usersever.common.exception.MemberErrorCode;
import com.yechan.usersever.common.exception.MemberException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class AES {

    @Value("${enviroment.encryption.secret-key}")
    private String encryptionKey;
    private static final String AES = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; // 실제 서버엔 가려야함

    public String encode(String plainText) {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);

        try {
            byte[] encryptionBytes = cipher.doFinal(plainText.getBytes(UTF_8.name()));
            return Base64.getEncoder().encodeToString(encryptionBytes);
        } catch (Exception e) {
            throw new MemberException(MemberErrorCode.DECRYPTION_FAIL);
        }
    }

    public String decode(String encodedText) {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
        byte[] decodedBytes;

        try {
            decodedBytes = Base64.getDecoder().decode(encodedText);
            return new String(cipher.doFinal(decodedBytes), UTF_8.name());
        } catch (Exception e) {
            throw new MemberException(MemberErrorCode.DECRYPTION_FAIL);
        }
    }

    private Cipher initCipher(int mode) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(UTF_8.name()), AES);
            IvParameterSpec iv = new IvParameterSpec(encryptionKey.substring(0, 16).getBytes(UTF_8.name()));
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, secretKey, iv);

            return cipher;
        } catch (Exception e) {
            throw new MemberException(MemberErrorCode.INITIALIZE_CHIPHER);
        }
    }
}