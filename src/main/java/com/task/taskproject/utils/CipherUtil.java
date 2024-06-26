package com.task.taskproject.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class CipherUtil {
    private final String initialVector = "SeedsSoftGdvKey1";
    private String secretKey;

    public CipherUtil() { }
    public CipherUtil(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getInitialVector() {
        return this.initialVector;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String decryptString(String str) throws InvalidKeyException, InvalidAlgorithmParameterException {
        return new String(this.decryptByteArray(str), StandardCharsets.UTF_8);
    }

    public byte[] decryptByteArray(String str) throws InvalidKeyException, InvalidAlgorithmParameterException {
        String initialVector = Optional.ofNullable(this.getInitialVector()).orElseThrow(() -> new InvalidAlgorithmParameterException("InitialVector unset"));
        String secretKey = Optional.ofNullable(this.getSecretKey()).orElseThrow(() -> new InvalidKeyException("SecretKey unset"));
        return CryptoUtil.decrypt(initialVector, secretKey, str);
    }

    public String encryptString(String str) throws InvalidKeyException, InvalidAlgorithmParameterException {
        return Base64.encodeBase64String(this.encryptByteArray(str));
    }

    public byte[] encryptByteArray(String str) throws InvalidKeyException, InvalidAlgorithmParameterException {
        String initialVector = Optional.ofNullable(this.getInitialVector()).orElseThrow(() -> new InvalidAlgorithmParameterException("InitialVector unset"));
        String secretKey = Optional.ofNullable(this.getSecretKey()).orElseThrow(() -> new InvalidKeyException("SecretKey unset"));
        return CryptoUtil.encrypt(initialVector, secretKey, str);
    }

    public byte[] oneWayEncryptByteArray(String str) throws NoSuchAlgorithmException {
        return CryptoUtil.oneWayEncrypt(str);
    }

    public String oneWayEncryptString(String str) throws NoSuchAlgorithmException {
        return this.bytesToHex(CryptoUtil.oneWayEncrypt(str));
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) stringBuilder.append(String.format("%02x", b));
        return stringBuilder.toString();
    }
}
