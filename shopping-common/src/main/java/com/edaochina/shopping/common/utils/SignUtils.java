package com.edaochina.shopping.common.utils;

import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author :         Jason
 * @createDate :     2018/10/13 14:44
 * @description :   签名生成工具
 */
@UtilityClass
public class SignUtils {

    /**
     * SHA256 签名算法
     */
    public static final String SHA256 = "SHA-256";

    /**
     * md5 签名算法
     */
    public static final String MD5 = "md5";

    /**
     * 生成签名 默认使用 md5
     *
     * @param value 要转换成签名的数据
     * @return 生成后的签名
     * @throws NoSuchAlgorithmException 无指定签名类型异常
     */
    public String generateSign(String value) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.update(value.getBytes());
        return generate(messageDigest);
    }

    /**
     * 生成签名 默认使用 md5
     *
     * @param value       要转换成签名的数据
     * @param charsetName 字符集，默认为 UTF-8
     * @return 生成后的签名
     * @throws NoSuchAlgorithmException 无指定签名类型异常
     */
    public String generateSignByCharset(String value, String charsetName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.update(value.getBytes(charsetName));
        return generate(messageDigest);
    }

    /**
     * 生成签名
     *
     * @param value 要转换成签名的数据
     * @param type  要转签名的类型
     * @return 生成后的签名
     * @throws NoSuchAlgorithmException 无指定签名类型异常
     */
    public String generateSign(String value, String type) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(type);
        messageDigest.update(value.getBytes());
        return generate(messageDigest);
    }

    /**
     * 生成签名
     *
     * @param value 要转换成签名的数据
     * @param type  要转签名的类型
     * @return 生成后的签名
     * @throws NoSuchAlgorithmException 无指定签名类型异常
     */
    public String generateSignByCharset(String value, String type, String charsetName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(type);
        messageDigest.update(value.getBytes(charsetName));
        return generate(messageDigest);
    }

    /**
     * 生成签名主要方法
     *
     * @param messageDigest 包含信息的信息摘要类
     * @return 生成后的签名
     */
    private String generate(MessageDigest messageDigest) {
        // 得到 byte 類型结果
        byte[] byteBuffer = messageDigest.digest();

        // 將 byte 轉換爲 string
        StringBuilder strHexString = new StringBuilder();
        // 遍歷 byte buffer
        for (byte singleByte : byteBuffer) {
            String hex = Integer.toHexString(0xff & singleByte);
            if (hex.length() == 1) {
                strHexString.append('0');
            }
            strHexString.append(hex);
        }
        // 返回結果
        return strHexString.toString();
    }
}
