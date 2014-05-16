/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */
package com.ncut.reg.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * ��־ϵͳ����ӿ���
 * <p>���е�ϵͳ��ͨ����������¼��־</p>
 * ������Ҫ��ϵͳ��ʼ����ʱ��ͨ������
 * LogHelper.initLogger()����һ�γ�ʼ�� �������е�Log4j������ü���
 * <br>
 * �˲��ֹ���������<font color="red">webģ��İ�װ����</font>ִ��
 * <p/>
 */
public class LogHelper {
    /**
     * ��ȡ�ض���Logger,����<font color="blue">�ȼ�</font>��¼��־
     * <p/>
     * ��ͬ�ȼ�����־���õ���ͬ��Appender��ȥ
     *
     * @param clazzName logger ����
     * <font color="blue">�������ͳһ��������ע�����������,��:
     * <br>�ڱ�ϵͳ�ڲ�����һ������,���ñ�ϵͳͨ�õ�Log�ַ���
     * <br>  �������ڱ�ϵͳ�ڲ����ñ��ַ���������ΪclazzName��
     * <br>Ϊ�˼���ϵͳ֮���ż���ԣ���־ϵͳ���ṩ������ϵͳ��ص�
     * <br> ����
     * </font>
     * @param logLevel  ��־�ȼ�  ����LogHelper���ṩ��Level������ʾ
     * @param message   ��־��Ϣ   Object ������־��ʽ <br>
     * <font color="blue">
     * message ��Ϊxml��ʽ��ʾ����־�����ֱ��ʽ��־����
     * </font>
     */
    private static StringWriter sw = new StringWriter();
    private static PrintWriter pw = new PrintWriter(sw);

    /**
     * ��¼�쳣��ջ��Ϣ,���԰Ѷ�ջ��Ϣ����ˣ����ڲ��Ҵ���
     *
     * @param clazzName
     * @param throwable
     * @deprecated please the stand log,just put the message you need into the message
     */
    public static void error(String clazzName, Throwable throwable) {
        String loggerName = ERROR + "." + clazzName;
        //��������,����logger����ͳһ������׼
        Logger tmpLogger = loggerMap.get(loggerName);
        if (tmpLogger == null) {
            tmpLogger = Logger.getLogger(loggerName);
            loggerMap.put(loggerName, tmpLogger);
        }
        synchronized (sw) {
            throwable.printStackTrace(pw);
            String message = sw.toString();
            tmpLogger.log(ApplogLevel.toLevel(ERROR), message);
            sw.getBuffer().setLength(0);
        }
    }

    public static void log(String clazzName, String logLevel, CharSequence message) {
        String loggerName = logLevel.toUpperCase() + "." + clazzName;
        //��������,����logger����ͳһ������׼
        Logger tmpLogger = loggerMap.get(loggerName);
        if (tmpLogger == null) {
            tmpLogger = Logger.getLogger(loggerName);
            loggerMap.put(loggerName, tmpLogger);
        }
        tmpLogger.log(LogHelper.class.getName(), ApplogLevel.toLevel(logLevel), message, null);
    }

    /**
     * ��ʼ�� ��־���� �� Log4j ����
     * ͨ�� System  Properties  ����ֵ
     * <p/>
     * <pre>
     * ע��: ����Ѿ���ϵͳ��ʹ��������log4jҲ�������³�ʼ����ϵͳ,����Ӱ��
     * <p/>
     * Key ��log4jconfigfile   Log4j�����ļ�ȫĿ¼����֧�ֱ����ļ�
     * Key��log4jconfigfile.watchdelay  �����־�����ļ��ı������
     * ��������ã��򲻼��.<font color="blue">������ϵͳ�������ڼ�,���ô˲���</font>
     * </pre>
     * <br>
     * change log:
     * <br>
     * 1.  �������Թ��� log4jconfigfile
     * 2.�޸Ĳ��ø�Ŀ¼�µ��ļ� towncarl 2007-09-05
     */
    public static void initLogger() {
        String delay = System.getProperty("log4jconfigfile.watchdelay");
        String log4jConfig = System.getProperty("log4jconfigfile");
        if (log4jConfig == null) {
            // ���ص�ǰ "Ŀ¼" �µ�Ĭ�������ļ�
            log4jConfig = (LogHelper.class).getResource("/Log4jConfig.xml").getFile();
            //todo change to use the internal loger
            LogLog.warn("Not found " +
                    " System`s properties :LogsystemLog4jConfigFile! Using the default config file in  \n" + log4jConfig);
        }
        if (TextParser.isEmpty(delay)) {
            delay = "0";
        }
        // �����µĽӿڽ��г�ʼ��
        initLogger(log4jConfig, Integer.parseInt(delay));
    }

    /**
     * ��ʼ�� ��־���� �� Log4j ����
     * ͨ������ �����ļ����ӳ�ֵ��Ϊ����
     *
     * @param configFileName String �����ļ�ȫ·��
     * @param delay          �� long ���뼶�����¼�������ļ��ı��ӳ� <=0 �����
     */
    public static void initLogger(String configFileName, int delay) {
        if (isLoad) {
            log(LOGGER, LogHelper.DEBUG, "��־ϵͳ�� Log4j �����ļ��Ѽ���,�����ظ�����!");
            return;
        }

        if (TextParser.isEmpty(configFileName)) {
            return;
        }
        //����ļ��Ƿ���URL��ʽ���ṩ������ṩ�����URL��ʽ����
        if (configFileName.startsWith("http")) {
            try {
                URL url = new URL(configFileName);
                DOMConfigurator.configure(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            isLoad = true;
            log(LOGGER, LogHelper.INFO, "������־ϵͳ�� Log4j �����ļ�{" + configFileName + "} �ɹ�!");
            return;
        }
        if (delay <= 0) {
            DOMConfigurator.configure(configFileName);
        } else {
            //ֻ�б��ص��ļ���֧�ֶ�̬���
            DOMConfigurator.configureAndWatch(configFileName, Long.parseLong(String.valueOf(delay)));
        }
        isLoad = true;
        log(LOGGER, LogHelper.INFO, "������־ϵͳ�� Log4j �����ļ�{" + configFileName + "} �ɹ�!");
    }

    /**
     * �����Ƿ��Ѽ��ع�Log4j����������
     */
    public static boolean isLoad = false;
    /**
     * �����Ѵ��ڵ�Logger
     */
    private static Map<CharSequence, Logger> loggerMap = new HashMap<CharSequence, Logger>();
    /**
     * �ṩ��־�ȼ�����������
     * OFF, OPERATION,LOGIN,FATAL, ERROR, WARN, INFO,DEBUG and ALL
     * �ر�,����,��¼,����,����,����,��Ϣ,����,ȫ��
     */
    public static final String OFF = "OFF";

    public static final String ALL = "ALL";
    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String FATAL = "FATAL";
    public static final String LOGIN = "LOGIN";
    public static final String OPERATION = "OPERATION";


    /**
     * This class`s logger,should accord to the class name
     */
    private static String LOGGER = "com.teamsun.ms.utils.LogHelper";
}
