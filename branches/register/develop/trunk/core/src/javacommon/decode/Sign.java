package javacommon.decode;

import java.security.PublicKey;
import java.security.Signature;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: ����2:27
 * To change this template use File | Settings | File Templates.
 */
public class Sign {
//    final static String KEYSTORE_FILE_A   = "E:/PKI/ѧϰ�׶γɹ��ռ�/ejbca֤��/shijianyi.p12";
    final static String KEYSTORE_FILE_A   = "E:/PKI/ѧϰ�׶γɹ��ռ�/ejbca֤��/2012-11-20/Client.p12";
    final static String KEYSTORE_PASSWORD_A = "Client";
    public static boolean verifySign(String data,String sign) throws Exception {
        PublicKey publicKeyA= ReadP12Cert.getPublicKey(KEYSTORE_FILE_A, KEYSTORE_PASSWORD_A);
        Signature signet= Signature.getInstance("SHA1withRSA");
        signet.initVerify(publicKeyA);
        //��msgBytes����ʵʩǩ��
        //byte[] dataByte=hexStrToBytes(data);
        //signet.update(dataByte);
        signet.update(data.getBytes());
        //byte[] signed = hexStrToBytes(sign);
        if (signet.verify(sign.getBytes())) {
            System.out.println("data:" + data);
            System.out.println("ǩ����");
            return true;
        }
        else{
            System.out.println("��ǩ����");
            return false;
        }
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


}
