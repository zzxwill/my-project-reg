/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */
package com.ncut.reg.utils;

import org.apache.log4j.Level;

/**
 * ʵ��������ε���־��¼����չLog4j ����־�ȼ�
 */
public class ApplogLevel extends Level
{
    //��¼���𣬱�FATAL��1��
    static public final int LOGIN_INT = Level.FATAL_INT + 1000;
    //�������𣬱�FATAL ��2��
    static public final int OPERATION_INT = Level.FATAL_INT + 2000;

    /**
     * Instantiate a Level object.
     * @param level   ��־����
     * @param levelStr  ��־���������ַ���
     * @param syslogEquivalent ��ϵͳ��־��ƥ��ļ���
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
     * @param sArg    ��־�����ַ���
     * @return   ���������ϵͳ��־
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
     * ���Ӷ� login��operation �Ĵ���
     * @param i  ������������������
     * @return     ���ظ澯����
     * @throws IllegalArgumentException   �������ϸ�
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
