package com.ncut.reg.utils;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 12-3-8
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return null == str ? true : false;
    }

    public static String convertNullString(String str) {
        return null == str ? "" : str;
    }
}
