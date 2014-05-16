package javacommon.decode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: ����2:29
 * To change this template use File | Settings | File Templates.
 */
public class AES {
    public static String decodeAES(String aesData,String aesKey) throws Exception{
        byte[] inputData=hexStrToBytes(aesData);
        byte[] key=hexStrToBytes(aesKey);
        //����
        byte[] outputData=AESCoder.decrypt(inputData,key);
        String outputStr=bytesToHexStr(outputData);
        String dataStr=outputStr.substring(0,outputStr.indexOf("00000aaaaa"));
        String signStr=outputStr.substring(outputStr.indexOf("00000aaaaa")+10,outputStr.indexOf("11111bbbbb"));
        String publickeyASt=outputStr.substring(outputStr.indexOf("11111bbbbb")+10,outputStr.length());
        return dataStr;
    }
    /**
     * Transform the specified Hex String into a byte array.
     */
    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }

    private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    /**
     * Transform the specified byte into a Hex String form.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }
        return s.toString();
    }
}
