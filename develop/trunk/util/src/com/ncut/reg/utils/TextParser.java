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
     * �����Ľ��д���
     *
     * @param chinese ���ܰ������ĵ��ַ���
     * @return �������ַ���
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
     * ת������ΪISO��׼����
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
     * �ж��ַ��������Ƿ�<font color="blue">��</font>Ϊnul���Ƿ�<font color="blue">��</font>Ϊ�ַ���"null"
     *
     * @param sstring �жϵ��ַ���
     * @return boolean  ��null �򷵻�true��null�򷵻�false
     */
    public static boolean isNotNull(final String sstring)
    {
        return !isNull(sstring);
    }

    /**
     * �ж��ַ����Ƿ�Ϊnul���Ƿ�Ϊ�ַ���"null"
     *
     * @param sstring �жϵ��ַ���
     * @return boolean null �򷵻�true����null�򷵻�false
     *         change log:
     *         1. �޸��ַ����ıȽϹ��� �������� null �� "null" 2004-2-27 Towncarl
     */
    public static boolean isNull(final String sstring)
    {
        return (sstring == null || "null".equals(sstring.trim()));
    }


    /**
     * �ж��ַ����Ƿ�Ϊ<font color="blue">��</font>
     * �� ���� [null][" "]{*}["null"][" "]{*}[" "]{*}
     *
     * @param sstring �жϵ��ַ���
     * @return boolean �� �򷵻�true���ǿ��򷵻�false
     *         change log:
     *         1. add 2004-3-3 Towncarl
     */
    public static boolean isEmpty(final String sstring)
    {
        return isNull(sstring) || "".equals(sstring.trim());
    }

    /**
     * �ж��ַ����Ƿ�<font color="red">��</font><font color="blue">��</font>
     * �� ���� [null][" "]{*}["null"][" "]{*}[" "]{*}
     *
     * @param sstring �жϵ��ַ���
     * @return boolean ���� �򷵻�true�����򷵻�false
     *         change log:
     *         1. add 2004-3-3 Towncarl
     */
    public static boolean isNotEmpty(final String sstring)
    {
        return !isEmpty(sstring);
    }

    /**
     * �ж��ַ����Ƿ�Ϊ��,������Ĭ��ֵ
     *
     * @param sstring  �жϵ��ַ���
     * @param sdefault Ĭ��ֵ
     * @return String      ����ǿգ��򷵻�Ĭ��ֵ�����򷵻�ԭ�ַ���
     *         <br />
     *         change log:
     *         1.����Ϊ����isNull�����ڲ��ж�
     */
    public static String replaceNullWith(String sstring, String sdefault)
    {
        return isNull(sstring) ? sdefault : sstring;
    }

    /**
     * �ж��ַ����Ƿ�Ϊ��,������Ĭ��ֵ
     *
     * @param sstring  �жϵ��ַ���
     * @param sdefault Ĭ��ֵ
     * @return String      ����ǿգ��򷵻�Ĭ��ֵ�����򷵻�ԭ�ַ���
     */
    public static String replaceEmptyWith(String sstring, String sdefault)
    {
        return isEmpty(sstring) ? sdefault : sstring;
    }

    /**
     * �滻һ�������е�ĳ���ַ�
     *
     * @param content һ������
     * @param oldWord Ҫ�滻���ַ�
     * @param newWord �滻����ַ�
     * @return �滻�������
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
     * �滻�ո�ͻس�����<table  /table>�е�html���ݲ�������
     *
     * @param ls_content ԭ�����ı�
     * @return �����������ı�
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
     * ����Ψһ��ID
     */
    public static synchronized String generateUID()
    {
        TextParser.UIDCounter++;
        return String.valueOf(System.currentTimeMillis()) +
                String.valueOf(UIDCounter);
    }


    /**
     * ��ָ�����ַ���ǰ��0�Դﵽָ���ĳ���
     *
     * @param id   �������ַ���
     * @param leng ��䳤��
     * @return ����������ַ���
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
     * �滻�ı��е������ַ�Ϊ��ʾ��xml��ʽ���������У�ÿ����Ϊһ��Ԫ��
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
     * �滻�ı��е������ַ�Ϊ��ʾ��xml��ʽ���������У�ÿ����Ϊһ��Ԫ��
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
     * ѹ�������ַ��е��ظ�������
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
     * �����ַ���
     *
     * @param pwd Ҫ���ܵ��ַ���
     * @return ���ܺ��32λ�ַ���
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
     * ��ǩ�������ַ���
     *
     * @param userid ǩ��
     * @param pwd    Ҫ���ܵ��ַ���
     * @return ���ܺ��32λ�ַ���
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
     * ���ַ���֮ǰ��0
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
     * 2�����ַ�����10���Ƶ�ת��
     *
     * @param binstr �������Զ��ŷָ�"1,1,1,1,1"��"11111"
     * @return int ת�����10��������
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
     * 10�����ַ�ת��Ϊ7λ�Ķ������ַ���
     *
     * @param i
     * @return String
     */
    public static String intToBinstr(int i)
    {
        return pad(Integer.toBinaryString(i), 7);
    }

    /**
     * ��һ����һ�ַ�ת��Ϊ��,�ŷָ���ַ���
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
     * ���Ƶ�����Ϣ�����
     *
     * @param message Ҫ�������Ϣ
     * @param isprint �Ƿ����
     */
    public static void debug(String message, boolean isprint)
    {
        if (isprint)
        {
            System.out.println(message);
        }
    }

    /**
     * �����Ϣ
     *
     * @param message Ҫ�������Ϣ
     * @deprecated ɾ�����취����ר�ż�¼��־�ĳ���������
     *             �˷���ֻ���������򵥵�ϵͳ�ڲ����Է���
     *             ������system.debug ������
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
     * �Ƚ϶����Ƿ����
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
     * �ѻس�ת��ΪHTML��ʽ��&lt;br&gt;
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
     * set���͵�����ת��Ϊ�Զ��ŷָ�
     *
     * @param obj ��rs��ʹ��rs.getObject("colname")���
     * @return String �Զ��ŷָ����ַ���                                                j
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
     * ���Զ��ŷָ����ַ���ת��Ϊset���͵��ַ���
     * ���ڲ������ݿ��е�Set����
     *
     * @param css ���ŷָ����ַ���
     * @return set ���͵��ַ���
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
     * �γ���ȫƥ������ڱ��ʽ����,�ŷָ�
     *
     * @param value Ҫ�������ڱ��ʽ��ֵ
     * @return String ��ͷ|�м�|��β
     */
    public static String setTotalMatchRegexp(String value)
    {
        return "^" + value + "$|^" + value + ",|," + value + ",|," + value + "$";
    }

    /**
     * ����Զ��ŷָ���Դ�ַ����Ƿ����Ŀ���ַ�
     *
     * @param sourceStr ��ƥ���ַ���
     * @param targetStr ƥ���ַ���
     * @return boolean  ���sourceStr�а���targetStr ����ture ���򷵻�false
     */
    public static boolean find(String sourceStr, String targetStr)
    {
        return Pattern.compile(setTotalMatchRegexp(targetStr)).matcher(sourceStr).find();
    }

    /**
     * ����������ʽ�滻Դ�ַ����е�ĳ�ַ�
     *
     * @param sourceStr    Դ�ַ���
     * @param toBeReplaced ���滻�ַ�
     * @param replacement  �滻�ַ�
     * @return �滻����ַ���
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
         * �滻Դ�ַ����еĵ�����
         *
         * @param sourceStr    Դ�ַ���
         * @return �滻����ַ���
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
     * ��֤�Ƿ�������
     *
     * @param value  ԭʼ����,������trim��ѹ��
     * @param length ���ֳ���,��ȫƥ��
     * @return true �� false ����
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
     * ��֤�Ƿ�������
     *
     * @param value ԭʼ����,������trim��ѹ��
     * @return true �� false ����
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