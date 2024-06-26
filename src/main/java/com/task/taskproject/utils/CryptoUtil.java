package com.task.taskproject.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {

    /*
     * KISA_SEED_CBC 복호화 알고리즘
     */
    public static byte[] decrypt(String initialVector, String secretKey, String str) {
        byte[] strBytes = Base64.decodeBase64(str);
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] InitialVectorBytes = initialVector.getBytes(StandardCharsets.UTF_8);
        return KISA_SEED_CBC.SEED_CBC_Decrypt(secretKeyBytes, InitialVectorBytes, strBytes, 0, strBytes.length);
    }

    /*
     * KISA_SEED_CBC 암호화 알고리즘
     */
    public static byte[] encrypt(String initialVector, String secretKey, String str) {
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] InitialVectorBytes = initialVector.getBytes(StandardCharsets.UTF_8);
        return KISA_SEED_CBC.SEED_CBC_Encrypt(secretKeyBytes, InitialVectorBytes, strBytes, 0, strBytes.length);
    }

    /*
     * SHA-256 단방향 암호화 알고리즘
     */
    public static byte[] oneWayEncrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes());
        return messageDigest.digest();
    }
}
