package com.vnext.custom.demo.starter.utils;

/**
 * @author leo
 * @version 1.0.0
 * @since 2018/9/6 14:09
 */
public class StringUtil {


    /**
     * Description: 除去空字符串, 消除字符串两边空格
     * @param str String字符串
     * @return
     */
    public String dealNull(String str) {
        return str == null ? "" : str.trim();
    }

}
