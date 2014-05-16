package com.ncut.reg.utils;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * change log:
 * 1.add encode method ,only encode the password 2003-12-23
 * 2.change the isNull method 2004-2-27 Towncarl
 * 3. add the replaceEmptyWith method,modify the realize of replaceNullWith
 */
public class TextParser {
    /**
     * 对中文进行处理
     *
     * @param chinese 可能包含中文的字符串
     * @return 处理后的字符串
     */
    public static String processChinese(String chinese)
    {

        try
        {
            byte[] byteArray = chinese.getBytes("GBK");
            chinese = new String(byteArray, "ISO8859_1");
        } catch (Exception e)
        {

        }
        return chinese;
    }

    /**
     * @param chinese
     * @return
     */
    public static String processChineseOther(String chinese)
    {

        try
        {
            byte[] byteArray = chinese.getBytes("ISO-8859-1");
            chinese = new String(byteArray, "GB2312");
        } catch (Exception e)
        {

        }
        return chinese;
    }

    /**
     * 转换中文为ISO表准编码
     */
    public static String processISO(String chinese)
    {

        try
        {
            byte[] byteArray = chinese.getBytes("GB2312");
            chinese = new String(byteArray, "ISO-8859-1");
        } catch (Exception e)
        {

        }
        return chinese;
    }

    /**
     * 判断字符串对象是否<font color="blue">不</font>为nul或是否<font color="blue">不</font>为字符串"null"
     *
     * @param sstring 判断的字符串
     * @return boolean  非null 则返回true，null则返回false
     */
    public static boolean isNotNull(final String sstring)
    {
        return !isNull(sstring);
    }

    /**
     * 判断字符串是否为nul或是否为字符串"null"
     *
     * @param sstring 判断的字符串
     * @return boolean null 则返回true，非null则返回false
     *         change log:
     *         1. 修改字符串的比较规则 仅仅处理 null 和 "null" 2004-2-27 Towncarl
     */
    public static boolean isNull(final String sstring)
    {
        return (sstring == null || "null".equals(sstring.trim()));
    }


    /**
     * 判断字符串是否为<font color="blue">空</font>
     * 空 代表 [null][" "]{*}["null"][" "]{*}[" "]{*}
     *
     * @param sstring 判断的字符串
     * @return boolean 空 则返回true，非空则返回false
     *         change log:
     *         1. add 2004-3-3 Towncarl
     */
    public static boolean isEmpty(final String sstring)
    {
        return isNull(sstring) || "".equals(sstring.trim());
    }

    /**
     * 判断字符串是否<font color="red">不</font><font color="blue">空</font>
     * 空 代表 [null][" "]{*}["null"][" "]{*}[" "]{*}
     *
     * @param sstring 判断的字符串
     * @return boolean 不空 则返回true，空则返回false
     *         change log:
     *         1. add 2004-3-3 Towncarl
     */
    public static boolean isNotEmpty(final String sstring)
    {
        return !isEmpty(sstring);
    }

    /**
     * 判断字符串是否为空,并赋上默认值
     *
     * @param sstring  判断的字符串
     * @param sdefault 默认值
     * @return String      如果是空，则返回默认值，否则返回原字符串
     *         <br />
     *         change log:
     *         1.更改为采用isNull进行内部判断
     */
    public static String replaceNullWith(String sstring, String sdefault)
    {
        return isNull(sstring) ? sdefault : sstring;
    }

    /**
     * 判断字符串是否为空,并赋上默认值
     *
     * @param sstring  判断的字符串
     * @param sdefault 默认值
     * @return String      如果是空，则返回默认值，否则返回原字符串
     */
    public static String replaceEmptyWith(String sstring, String sdefault)
    {
        return isEmpty(sstring) ? sdefault : sstring;
    }

    /**
     * 替换一段文字中的某个字符
     *
     * @param content 一段文字
     * @param oldWord 要替换的字符
     * @param newWord 替换后的字符
     * @return 替换后的文字
     */
    public static String replace(String content, String oldWord, String newWord)
    {
        String tempString = new String(content);
        int position = tempString.indexOf(oldWord);
        while (position > -1)
        {
            tempString = tempString.substring(0, position) + newWord
                    + tempString.substring(position + oldWord.length());
            position = tempString.indexOf(oldWord, position + newWord.length());
        }
        return tempString;
    }

    /**
     * 替换空格和回车，对<table  /table>中的html内容不做处理
     *
     * @param ls_content 原内容文本
     * @return 处理后的内容文本
     */
    public static String escapeHtmlTag(String ls_content)
    {
        if (ls_content == null || ls_content.length() == 0)
        {
            return ls_content;
        }
        int li_len = ls_content.length();
        int i = 0;
        String ls_newcon = "";
        String ls_token = "";

        int theTable = 0;
        for (i = 0; i < li_len; i++)
        {
            ls_token = "";
            char lc = ls_content.charAt(i);
            if (lc == '<')
            {
                String ls_temp = ls_content.substring(i, i + 6);

                if (ls_temp.equalsIgnoreCase("<TABLE"))
                {
                    theTable = theTable + 1;
                }
                if (ls_temp.equalsIgnoreCase("</TABL"))
                {
                    theTable = theTable - 1;
                }

            }
            if (theTable > 0)
            {
                ls_newcon = ls_newcon + ls_content.charAt(i);
                continue;
            }
            if (lc == ' ')
            {
                ls_token = ls_token + "&nbsp;";
            } else if (lc == '\r')
            {
                ls_token = ls_token + "<br>";
            } else if (lc == '\t')
            {
                ls_token = ls_token + "&nbsp;&nbsp;";
            } else if (lc == '\n')
            {
            } else
            {
                ls_token = ls_token + lc;
            }
            ls_newcon = ls_newcon + ls_token;
        }
        return ls_newcon;
    }

    private static long UIDCounter = System.currentTimeMillis();

    /**
     * 产生唯一的ID
     */
    public static synchronized String generateUID()
    {
        TextParser.UIDCounter++;
        return String.valueOf(System.currentTimeMillis()) +
                String.valueOf(UIDCounter);
    }


    /**
     * 在指定的字符串前加0以达到指定的长度
     *
     * @param id   待检验字符串
     * @param leng 填充长度
     * @return 经过填充后的字符串
     * @deprecated use the pad(String ,int length)
     */
    public static String addZero(int id, int leng)
    {
        String sid = String.valueOf(id);
        if (sid.length() != leng)
        {
            int pack = leng - sid.length();
            for (int i = 0; i < pack; i++)
            {
                sid = "0" + sid;
            }
            return sid;
        } else
        {
            return sid;
        }
    }

    /**
     * 替换文本中的特殊字符为显示的xml格式，遇到换行，每行作为一个元素
     *
     * @param text
     * @return
     */
    public static ArrayList<? extends String> xmlEncode(String text)
    {
        if (text == null)
        {
            return null;
        }
        StringBuffer results = new StringBuffer();
        ArrayList<String> list = new ArrayList<String>();
        int beg = 0, len = text.length();
        for (int i = 0; i < len; i++)
        {
            char c = text.charAt(i);
            switch (c)
            {
                case '&':
                    results.append("&amp;");
                    break;
                case '<':
                    results.append("&lt;");
                    break;
                case '>':
                    results.append("&gt;");
                    break;
                case '"':
                    results.append("&quot;");
                    break;
                case ' ':
                    results.append("&#9;");
                    break;
                case '\n':
                {
                    list.add(results.toString());
                    results.delete(0, results.capacity());
                }
                break;
                default:
                    results.append(String.valueOf(c));
            }

        } // for i
        if (results == null)
        {
            return list;
        }

        list.add(results.toString());

        return list;
    }

    /**
     * 替换文本中的特殊字符为显示的xml格式，遇到换行，每行作为一个元素
     *
     * @param text
     * @return
     */
    public static String escapeXmlTag(String text)
    {
        if (text == null)
        {
            return null;
        }
        StringBuffer results = new StringBuffer();
        int beg = 0, len = text.length();
        for (int i = 0; i < len; i++)
        {
            char c = text.charAt(i);
            switch (c)
            {
                case '&':
                    results.append("&amp;");
                    break;
                case '<':
                    results.append("&lt;");
                    break;
                case '>':
                    results.append("&gt;");
                    break;
                case '"':
                    results.append("&quot;");
                    break;
                case ' ':
                    results.append("&#9;");
                    break;
                default:
                    results.append(String.valueOf(c));
            }

        }


        return results.toString();
    }

    /**
     * 压缩输入字符中的重复的数据
     *
     * @param inputstr
     * @return outstr
     *         <pre>
     *                                                 change log:
     *                                                 1.created and fix the bug 2003-12 Towncarl
     *                                                 2.fixed the bug 2004-03-22 Towncarl
     *                                         </pre>
     */
    public static String compress(String inputstr, String delimiter)
    {
        if (isEmpty(inputstr))
        {
            return inputstr;
        }
        StringBuffer outstr = new StringBuffer();
        String[] arrtemp = inputstr.split(delimiter + "+");
        int length = arrtemp.length;
        for (int i = 0; i < length; i++)
        {
            if (arrtemp[i] != "")
            {
                outstr.append(delimiter + arrtemp[i]);
                for (int j = i + 1; j < length; j++)
                {
                    if (arrtemp[i].equals(arrtemp[j]))
                    {
                        arrtemp[j] = "";
                    }
                }
            }
        }
        String result = outstr.toString();

        if (result.startsWith(delimiter))
        {
            result = result.replaceFirst(delimiter + "+", "");
        }
        return result;

    }

    /**
     * 加密字符串
     *
     * @param pwd 要加密的字符串
     * @return 加密后的32位字符串
     */
    public static String encode(String pwd)
    {
        byte buf[] = pwd.getBytes();
        StringBuffer hexString = new StringBuffer();
        try
        {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(buf);
            byte[] digest = algorithm.digest();
            for (int i = 0; i < digest.length; i++)
            {
                hexString.append(pad(Integer.toHexString(0xFF & digest[i]), 2));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    /**
     * 带签名加密字符串
     *
     * @param userid 签名
     * @param pwd    要加密的字符串
     * @return 加密后的32位字符串
     */
    public static String encodeWithKey(String userid, String pwd)
    {
        byte buf[] = pwd.getBytes();
        byte key[] = userid.getBytes();
        StringBuffer hexString = new StringBuffer();
        try
        {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(buf);
            byte[] digest = algorithm.digest(key);
            for (int i = 0; i < digest.length; i++)
            {
                hexString.append(pad(Integer.toHexString(0xFF & digest[i]), 2));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    /**
     * 在字符串之前补0
     *
     * @param i
     * @param l
     * @return
     */
    public static String pad(String i, int l)
    {
        while (i.length() < l)
        {
            i = '0' + i;
        }
        return i;
    }

    /**
     * 2进制字符串到10进制的转换
     *
     * @param binstr 可以是以逗号分隔"1,1,1,1,1"或"11111"
     * @return int 转换后的10进制数据
     */
    public static int binstrToInt(String binstr)
    {
        String temp = binstr;
        if (temp.indexOf(',') != -1)
        {
            temp = temp.replaceAll(",", "");
        }
        return Integer.valueOf(temp, 2).intValue();
    }


    /**
     * int to binstr
     * 10进制字符转化为7位的二进制字符串
     *
     * @param i
     * @return String
     */
    public static String intToBinstr(int i)
    {
        return pad(Integer.toBinaryString(i), 7);
    }

    /**
     * 把一个单一字符转换为以,号分割的字符串
     *
     * @param csv
     * @return
     */
    public static String stringToCSS(String csv)
    {
        StringBuffer sboutstr = new StringBuffer();
        char[] tempca = csv.toCharArray();
        int length = tempca.length;
        if (length > 0)
        {
            sboutstr.append(tempca[0]);
        }

        for (int i = 1; i < length; i++)
        {
            sboutstr.append("," + tempca[i]);
        }

        return sboutstr.toString();
    }

    /**
     * 控制调试信息的输出
     *
     * @param message 要输出的信息
     * @param isprint 是否输出
     */
    public static void debug(String message, boolean isprint)
    {
        if (isprint)
        {
            System.out.println(message);
        }
    }

    /**
     * 输出消息
     *
     * @param message 要输出的信息
     * @deprecated 删除本办法，由专门记录日志的程序来处理
     *             此方法只可以用做简单的系统内部测试方法
     *             开关由system.debug 来决定
     *             change log:
     *             1.config to read the debug or not from the System.Properties
     */
    public static void debug(String message)
    {
        //change to read the debug or not from the config file 2004-2- 25 Towncarl
        if ("true".equals(System.getProperty("system.debug")))
        {
            debug(message, true);
        }
    }

    /**
     * 比较对象是否相等
     *
     * @param fromObj
     * @param toObj
     * @return boolean
     */
    public static boolean compareObj(Object fromObj, Object toObj)
    {
        boolean result = false;
        if (fromObj == null)
        {
            if (toObj == null)
            {
                result = true;
            }
        } else
        {
            if (toObj != null)
            {
                result = fromObj.equals(toObj) ? true : false;
            }
        }

        return result;
    }

    /**
     * 把回车转化为HTML格式的&lt;br&gt;
     *
     * @param sStr
     * @return String
     */
    public static String returnToBr(String sStr)
    {
        if (sStr == null || sStr.equals(""))
        {
            return sStr;
        }

        String sTmp = new String();
        int i = 0;

        while (i <= sStr.length() - 1)
        {
            if (sStr.charAt(i) == '\n')
            {
                sTmp = sTmp.concat("<br>");
            } else if (sStr.charAt(i) == '\r')
            {
            } else
            {
                sTmp = sTmp.concat(sStr.substring(i, i + 1));
            }
            i++;
        }
        return sTmp;
    }

    /**
     * set类型的数据转换为以逗号分隔
     *
     * @param obj 在rs中使用rs.getObject("colname")获得
     * @return String 以逗号分隔的字符串                                                j
     */
    public static String setToCSS(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        String lsResult = obj.toString();
        debug(lsResult);
        lsResult = lsResult.replaceAll("^\\[|\\]$| ", "");
        return lsResult;
    }

    /**
     * 把以逗号分隔的字符串转化为set类型的字符串
     * 用于插入数据库中的Set类型
     *
     * @param css 逗号分隔的字符串
     * @return set 类型的字符串
     * @throws Exception
     */
    public static String cssToSet(String css) throws Exception
    {
        if (css != null && (!css.equals("null")) && (!css.equals("")))
        {
            return " set{" + css + "}";
        }
        //return null instead of css ,2003-12-25 Towncarl
        //return ""  2004-1-6
        return null;
    }

    /**
     * 形成完全匹配的正在表达式，以,号分割
     *
     * @param value 要生成正在表达式的值
     * @return String 开头|中间|结尾
     */
    public static String setTotalMatchRegexp(String value)
    {
        return "^" + value + "$|^" + value + ",|," + value + ",|," + value + "$";
    }

    /**
     * 检查以逗号分隔的源字符中是否包含目标字符
     *
     * @param sourceStr 被匹配字符串
     * @param targetStr 匹配字符串
     * @return boolean  如果sourceStr中包括targetStr 返回ture 否则返回false
     */
    public static boolean find(String sourceStr, String targetStr)
    {
        return Pattern.compile(setTotalMatchRegexp(targetStr)).matcher(sourceStr).find();
    }

    /**
     * 采用正则表达式替换源字符串中的某字符
     *
     * @param sourceStr    源字符串
     * @param toBeReplaced 待替换字符
     * @param replacement  替换字符
     * @return 替换后的字符串
     */
    public static String replaceALL(String sourceStr, String toBeReplaced, String replacement)
    {
        if (TextParser.isEmpty(sourceStr))
        {
            return sourceStr;
        }
        return sourceStr.replaceAll(setTotalMatchRegexp(toBeReplaced), replacement);
    }
    /**
         * 替换源字符串中的单引号
         *
         * @param sourceStr    源字符串
         * @return 替换后的字符串
         */
        public static String replaceInverted(String sourceStr)
        {
            int newproxy=0;
            while(sourceStr.indexOf("'",newproxy) != -1){
                    newproxy=sourceStr.indexOf("'",newproxy);
                    String sourceStr1=sourceStr.substring(0,newproxy)+"\\";
                    String sourceStr2=sourceStr.substring(newproxy);
                    sourceStr=sourceStr1+sourceStr2;
                    newproxy+=2;
            }
            return sourceStr;
        }


    /**
     * 验证是否是数字
     *
     * @param value  原始数据,不进行trim和压缩
     * @param length 数字长度,完全匹配
     * @return true 是 false 不是
     */
    public static boolean isNumber(Object value, int length)
    {
        if (isNumber(value))
        {
            if (value.toString().length() == length)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证是否是数字
     *
     * @param value 原始数据,不进行trim和压缩
     * @return true 是 false 不是
     */
    public static boolean isNumber(Object value)
    {
        if (value == null || isNotEmpty(value.toString()))
        {
            if (value.toString().matches("[0-9]*"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public static String tidyXML(String srcString)
    {
     return srcString.replaceAll("[\r]+[\n]+","");  
    }

    /**
     * core routine
     */
    public static String ascii2native(String str)
    {
        if (str == null)
        {
            return str;
        }
        if (str.startsWith("/u"))
        {
            str = str.replaceAll("/", "\\\\");
        } else
        {
            return str;
        }
        String hex = "0123456789ABCDEF";
        StringBuffer buf = new StringBuffer();
        int ptn = 0;

        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (c == '\\' && i + 1 <= str.length() && str.charAt(i + 1) == '\\')
            {
                buf.append("\\\\");
                i += 1;
            } else if (c == '\\' && i + 6 <= str.length() && str.charAt(i + 1) == 'u')
            {
                String sub = str.substring(i + 2, i + 6).toUpperCase();
                int i0 = hex.indexOf(sub.charAt(0));
                int i1 = hex.indexOf(sub.charAt(1));
                int i2 = hex.indexOf(sub.charAt(2));
                int i3 = hex.indexOf(sub.charAt(3));

                if (i0 < 0 || i1 < 0 || i2 < 0 || i3 < 0)
                {
                    buf.append("\\u");
                    i += 1;
                } else
                {
                    byte[] data = new byte[2];
                    data[0] = i2b(i1 + i0 * 16);
                    data[1] = i2b(i3 + i2 * 16);
                    try
                    {
                        buf.append(new String(data, "UTF-16BE").toString());
                    } catch (Exception ex)
                    {
                        buf.append("\\u" + sub);
                    }
                    i += 5;
                }
            } else
            {
                buf.append(c);
            }
        }

        return buf.toString();
    }

    /**
     * binary to unsigned integer
     * <p/>
     *
     * @param b binary
     * @return unsined integer
     */
    private static int b2i(byte b)
    {
        return (int) ((b < 0) ? 256 + b : b);
    }

    /**
     * unsigned integer to binary
     * <p/>
     *
     * @param i unsigned integer
     * @return binary
     */
    private static byte i2b(int i)
    {
        return (byte) ((i > 127) ? i - 256 : i);
    }
}