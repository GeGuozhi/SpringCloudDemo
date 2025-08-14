package com.ggz.util;

import com.ggz.pojo.User;
import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工具类
 *
 * @author ggz on 2025/6/24
 */
public class normalUtil {

    public static String generateKey(String chinese, double number, String symbol) throws Exception {
        // 1. 空值处理：symbol为空时填充"NULL"
        String safeSymbol = (symbol == null || symbol.isEmpty()) ? "NULL" : symbol;

        // 2. 拼接参数：统一用"|"分隔避免歧义
        String rawData = chinese + "|" + number + "|" + safeSymbol;

        // 3. 计算SHA-256哈希（32字节）
        byte[] hash = MessageDigest.getInstance("SHA-256")
                .digest(rawData.getBytes(StandardCharsets.UTF_8));

        // 4. 取前16字节 → 转32位十六进制字符串
        StringBuilder hexBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) { // 只取前16字节（32位十六进制）
            hexBuilder.append(String.format("%02x", hash[i]));
        }
        String hexKey = hexBuilder.toString();

        // 5. 十六进制转十进制，并补足32位
        BigInteger bigInt = new BigInteger(hexKey, 16);
        BigInteger modValue = BigInteger.TEN.pow(32); // 10^32
        BigInteger truncated = bigInt.mod(modValue);  // 取模确保≤32位
        return String.format("%032d", truncated);
    }

    /**
     * test merge
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        System.out.println(String.valueOf(Integer.MAX_VALUE));

        List<User> users = Lists.newArrayList();
        users.add(new User("ggz",1));


    }


}