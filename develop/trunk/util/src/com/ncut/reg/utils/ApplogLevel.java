/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */
package com.ncut.reg.utils;

import org.apache.log4j.Level;

/**
 * 实现两个层次的日志记录，扩展Log4j 的日志等级
 */
public class ApplogLevel extends Level
{
    //登录级别，别FATAL高1级
    static public final int LOGIN_INT = Level.FATAL_INT + 1000;
    //操作级别，比FATAL 高2级
    static public final int OPERATION_INT = Level.FATAL_INT + 2000;

    /**
     * Instantiate a Level object.
     * @param level   日志级别
     * @param levelStr  日志级别描述字符串
     * @param syslogEquivalent 与系统日志向匹配的级别
     */
    public ApplogLevel(int level, String levelStr, int syslogEquivalent)
    {
        super(level, levelStr, syslogEquivalent);
    }

    private static String LOGIN_STR = "LOGIN";
    private static String OPERATION_STR = "OPERATION";


    public static final ApplogLevel LOGIN = new ApplogLevel(LOGIN_INT, LOGIN_STR, 7);
    public static final ApplogLevel OPERATION = new ApplogLevel(OPERATION_INT, OPERATION_STR,
            8);


    /**
     * Convert the string passed as argument to a level. If the
     * conversion fails, then this method returns {@link #DEBUG}.
     * @param sArg    日志描述字符串
     * @return   符合情况的系统日志
     */
    public   static Level toLevel(String sArg)
    {
        return toLevel(sArg, Level.DEBUG);
    }


    public    static Level toLevel(String sArg, Level defaultValue)
    {

        if (sArg == null)
        {
            return defaultValue;
        }
        String stringVal = sArg.toUpperCase();

        if (stringVal.equals(LOGIN_STR))
        {
            return ApplogLevel.LOGIN;
        } else if (stringVal.equals(OPERATION_STR))
        {
            return ApplogLevel.OPERATION;
        }

        return Level.toLevel(sArg, defaultValue);
    }

    /**
     * 增加对 login和operation 的处理
     * @param i  根据数字来决定级别
     * @return     返回告警级别
     * @throws IllegalArgumentException   参数不合格
     */
    public    static Level toLevel(int i) throws IllegalArgumentException
    {
        switch (i)
        {
            case LOGIN_INT:
                return ApplogLevel.LOGIN;
            case OPERATION_INT:
                return ApplogLevel.OPERATION;
        }
        return Level.toLevel(i);
    }
}
