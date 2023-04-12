package com.northcastle.utils;

import java.util.Random;

/**
 * @Author: northcastle
 * @CreateTime: 2023-03-24  11:46
 * @Description: 字符串的工具类
 */
public class StringUtile {

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public static String obtainRandomStr(int length){
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i ++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = obtainRandomStr(4);
        System.out.println("s = " + s);
    }
}
