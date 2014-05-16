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
 * 日志系统对外接口类
 * <p>所有的系统都通过本类来记录日志</p>
 * 此类需要在系统初始化的时候通过调用
 * LogHelper.initLogger()进行一次初始化 ，把所有的Log4j相关配置加载
 * <br>
 * 此部分工作由整个<font color="red">web模块的安装程序</font>执行
 * <p/>
 */
public class LogHelper {
    /**
     * 获取特定的Logger,根据<font color="blue">等级</font>记录日志
     * <p/>
     * 不同等级的日志放置到不同得Appender中去
     *
     * @param clazzName logger 名称
     * <font color="blue">建议采用统一的命名标注以提高命中率,如:
     * <br>在本系统内部定义一个常量,设置本系统通用的Log字符串
     * <br>  常量，在本系统内部采用本字符串常量做为clazzName。
     * <br>为了减少系统之间的偶合性，日志系统不提供与其它系统相关的
     * <br> 常量
     * </font>
     * @param logLevel  日志等级  采用LogHelper中提供的Level常量表示
     * @param message   日志消息   Object 类型日志格式 <br>
     * <font color="blue">
     * message 可为xml格式表示的日志对象或直接式日志对象
     * </font>
     */
    private static StringWriter sw = new StringWriter();
    private static PrintWriter pw = new PrintWriter(sw);

    /**
     * 记录异常堆栈信息,可以把堆栈信息打出了，便于查找错误
     *
     * @param clazzName
     * @param throwable
     * @deprecated please the stand log,just put the message you need into the message
     */
    public static void error(String clazzName, Throwable throwable) {
        String loggerName = ERROR + "." + clazzName;
        //很难命中,除非logger采用统一命名标准
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
        //很难命中,除非logger采用统一命名标准
        Logger tmpLogger = loggerMap.get(loggerName);
        if (tmpLogger == null) {
            tmpLogger = Logger.getLogger(loggerName);
            loggerMap.put(loggerName, tmpLogger);
        }
        tmpLogger.log(LogHelper.class.getName(), ApplogLevel.toLevel(logLevel), message, null);
    }

    /**
     * 初始化 日志配置 的 Log4j 配置
     * 通过 System  Properties  传递值
     * <p/>
     * <pre>
     * 注意: 如果已经在系统中使用了其他log4j也可以重新初始化本系统,互不影响
     * <p/>
     * Key ：log4jconfigfile   Log4j配置文件全目录，仅支持本地文件
     * Key：log4jconfigfile.watchdelay  监控日志配置文件改变的周期
     * 如果不配置，则不监控.<font color="blue">建议在系统试运行期间,配置此参数</font>
     * </pre>
     * <br>
     * change log:
     * <br>
     * 1.  采用属性惯例 log4jconfigfile
     * 2.修改采用根目录下的文件 towncarl 2007-09-05
     */
    public static void initLogger() {
        String delay = System.getProperty("log4jconfigfile.watchdelay");
        String log4jConfig = System.getProperty("log4jconfigfile");
        if (log4jConfig == null) {
            // 加载当前 "目录" 下的默认配置文件
            log4jConfig = (LogHelper.class).getResource("/Log4jConfig.xml").getFile();
            //todo change to use the internal loger
            LogLog.warn("Not found " +
                    " System`s properties :LogsystemLog4jConfigFile! Using the default config file in  \n" + log4jConfig);
        }
        if (TextParser.isEmpty(delay)) {
            delay = "0";
        }
        // 调用新的接口进行初始化
        initLogger(log4jConfig, Integer.parseInt(delay));
    }

    /**
     * 初始化 日志配置 的 Log4j 配置
     * 通过传递 配置文件和延迟值作为参数
     *
     * @param configFileName String 配置文件全路径
     * @param delay          　 long 毫秒级的重新检测配置文件改变延迟 <=0 不检测
     */
    public static void initLogger(String configFileName, int delay) {
        if (isLoad) {
            log(LOGGER, LogHelper.DEBUG, "日志系统的 Log4j 配置文件已加载,不再重复加载!");
            return;
        }

        if (TextParser.isEmpty(configFileName)) {
            return;
        }
        //检查文件是否以URL方式的提供，如果提供则采用URL方式配置
        if (configFileName.startsWith("http")) {
            try {
                URL url = new URL(configFileName);
                DOMConfigurator.configure(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            isLoad = true;
            log(LOGGER, LogHelper.INFO, "加载日志系统的 Log4j 配置文件{" + configFileName + "} 成功!");
            return;
        }
        if (delay <= 0) {
            DOMConfigurator.configure(configFileName);
        } else {
            //只有本地的文件才支持动态监控
            DOMConfigurator.configureAndWatch(configFileName, Long.parseLong(String.valueOf(delay)));
        }
        isLoad = true;
        log(LOGGER, LogHelper.INFO, "加载日志系统的 Log4j 配置文件{" + configFileName + "} 成功!");
    }

    /**
     * 决定是否已加载过Log4j的配置属性
     */
    public static boolean isLoad = false;
    /**
     * 保存已存在的Logger
     */
    private static Map<CharSequence, Logger> loggerMap = new HashMap<CharSequence, Logger>();
    /**
     * 提供日志等级的描述常量
     * OFF, OPERATION,LOGIN,FATAL, ERROR, WARN, INFO,DEBUG and ALL
     * 关闭,操作,登录,致命,错误,警告,消息,调试,全部
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
