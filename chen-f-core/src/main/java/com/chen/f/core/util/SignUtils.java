package com.chen.f.core.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * rsa签名验证工具类
 *
 * @author chen
 * @since 2017/12/15 10:48.
 */
public class SignUtils {
    /**
     * 默认编码
     */
    private static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * 生成签名
     *
     * @param data          签名的数据
     * @param rsaPrivateKey rsa私钥
     * @return 签名字符串
     */
    public static String sign(final Map<String, Object> data, final String rsaPrivateKey) {
        String dataStr = SignUtils.generationSignStr(data);
        return RsaUtils.sign(dataStr, rsaPrivateKey);
    }


    /**
     * 验证签名
     *
     * @param data         签名的数据
     * @param signed       验证的签名
     * @param rsaPublicKey rsa公钥
     * @return 签名对/错
     */
    public static boolean check(final Map<String, Object> data, final String signed, final String rsaPublicKey) {
        String dataStr = SignUtils.generationSignStr(data);
        return RsaUtils.doCheck(dataStr, signed, rsaPublicKey);
    }


    /**
     * 生成签名 MD5
     *
     * @param data 签名的数据
     * @param key  MD5 key
     * @return 签名字符串
     */
    public static String signMd5(Map<String, Object> data, String key) {
        String dataStr = SignUtils.generationSignStr(data);
        dataStr = dataStr + key;
        try {
            return DigestUtils.md5DigestAsHex(dataStr.getBytes(DEFAULT_ENCODE));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("编码错误");
        }
    }

    /**
     * 验证签名 MD5
     *
     * @param data   签名数据
     * @param signed 验证的签名
     * @param key    MD5 key
     * @return 签名对错
     */
    public static boolean checkMd5(Map<String, Object> data, final String signed, final String key) {
        String dataStr = SignUtils.generationSignStr(data);
        dataStr = dataStr + key;
        try {
            return DigestUtils.md5DigestAsHex(dataStr.getBytes(DEFAULT_ENCODE)).equals(signed);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("编码错误");
        }
    }


    /**
     * 生成签名字符串
     *
     * @param data 需要签名的数据
     * @return 拼成的字符串
     */
    private static String generationSignStr(Map<String, Object> data) {
        TreeMap<String, Object> treeMap = new TreeMap<>(data);
        StringBuilder dataStr = new StringBuilder();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            if ("sign".equals(entry.getKey())) {
                continue;
            }
            dataStr.append(entry.getValue());
        }
        return dataStr.toString();
    }
}
