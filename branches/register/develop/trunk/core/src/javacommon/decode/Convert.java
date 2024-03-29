package javacommon.decode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-9
 * Time: ����8:37
 * To change this template use File | Settings | File Templates.
 */
public class Convert {
    public static String byte2hex(byte[] b) { // 二进制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    public static byte[] hex2byte(String str) { // 字符串转二进制
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;

        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer
                        .decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "abfdsfsdffdssfsd";
        String result = "";
        result = byte2hex(str.getBytes());
        System.out.println("strbyte:"+str.getBytes());
        System.out.println("byte2hex:"+result);
        System.out.println("hex2byte:"+new String(hex2byte(result)));
        System.out.println(hex2byte(result).toString());
    }
}
