package com.zsr.utils;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/7/28 16:49
 */
public class StringUtil {

    public static boolean isEmpty(String s){
        return s == null||"".equals(s);
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }
}
