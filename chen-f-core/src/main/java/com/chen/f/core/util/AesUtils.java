package com.chen.f.core.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * aes工具类
 *
 * @author chen
 * @since 2018/8/9 14:51.
 */
public class AesUtils {

    private static final String AES = "AES";
    private static final String SHA1_PRNG = "SHA1PRNG";
    private static final int DEFAULT_AES_KEY_SIZE = 128;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * aes加密，为base64String
     *
     * @param password 密码
     * @param content  加密内容
     * @return 加密后内容字符串格式
     */
    public static String encodeAsBase64String(String password, String content) {
        return encodeAsBase64String(password, content, null, null);
    }

    /**
     * aes加密，为base64String
     *
     * @param password 密码
     * @param content  加密内容
     * @param keySize  加密key长度
     * @param charset  字符编码
     * @return 加密后内容字符串格式
     */
    public static String encodeAsBase64String(String password, String content, Integer keySize, Charset charset) {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        byte[] encode = encode(password.getBytes(charset), content.getBytes(charset), keySize);
        return new String(Base64.getEncoder().encode(encode), charset);
    }

    /**
     * aes解密
     *
     * @param password 密码
     * @param content  加密内容
     * @return 加密后内容
     */
    public static byte[] encode(byte[] password, byte[] content) {
        return encode(password, content, null);
    }

    /**
     * aes解密
     *
     * @param password 密码
     * @param content  加密内容
     * @param keySize  加密key长度
     * @return 加密后内容
     */
    public static byte[] encode(byte[] password, byte[] content, Integer keySize) {
        try {
            if (keySize == null) {
                keySize = DEFAULT_AES_KEY_SIZE;
            }
            SecretKey key = generateSecretKey(password, keySize);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes解密
     *
     * @param password 密码
     * @param content  内容
     * @return 解密后的内容
     */
    public static byte[] decode(byte[] password, byte[] content) {
        return decode(password, content, null);
    }

    /**
     * aes解密
     *
     * @param password 密码
     * @param content  内容
     * @param keySize  key大小
     * @return 解密后的内容
     */
    public static byte[] decode(byte[] password, byte[] content, Integer keySize) {
        try {
            if (keySize == null) {
                keySize = DEFAULT_AES_KEY_SIZE;
            }
            SecretKey key = generateSecretKey(password, keySize);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes解密base64字符串内容，为string
     *
     * @param password 字符串密码
     * @param content  字符串内容
     * @return 解密后内容字符串格式
     */
    public static String decodeBase64StringContentAsString(String password, String content) {
        return decodeBase64StringContentAsString(password, content, null, null);
    }


    /**
     * aes解密base64字符串内容，为string
     *
     * @param password 字符串密码
     * @param content  字符串内容
     * @param keySize  key大小
     * @param charset  编码
     * @return 解密后内容字符串格式
     */
    public static String decodeBase64StringContentAsString(String password, String content, Integer keySize, Charset charset) {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        byte[] decode = decode(password.getBytes(charset), Base64.getDecoder().decode(content.getBytes(charset)), keySize);
        return new String(decode, charset);
    }


    /**
     * 生成SecretKey
     *
     * @param password 密码
     * @param keySize  key长度
     * @return SecretKey
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static SecretKeySpec generateSecretKey(byte[] password, Integer keySize) throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(AES);
        SecureRandom random = SecureRandom.getInstance(SHA1_PRNG);
        random.setSeed(password);
        keygen.init(keySize, random);
        SecretKey secretKey = keygen.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), AES);
    }

}
