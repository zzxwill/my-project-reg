package javacommon.decode;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.pkcs.pkcs7.ContentInfo;
import iaik.pkcs.pkcs7.EnvelopedData;
import iaik.pkcs.pkcs7.RecipientInfo;
import iaik.pkcs.pkcs7.SignedData;
import iaik.pkcs.pkcs7.SignerInfo;
import iaik.security.cipher.SecretKey;
import iaik.x509.X509Certificate;
import javacommon.ejbca.EJBCAClientImpl;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-14
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class DecodeUtil {
    /**
     * 作用：取得接受方（服务器）证书公钥
     * 时间：2013年1月14日9:07:37
     * 作者：史建宜
     * @return
     * @throws CertificateEncodingException
     */
    public static String getPublicKeyB() throws CertificateEncodingException
    {
        //取得证书
        EJBCAClientImpl ejbcaClientImpl=new EJBCAClientImpl();
        List<java.security.cert.Certificate> certs=ejbcaClientImpl.findCerts("Server", true);
        String base64cert=null;
        if(certs.size()>0){
            java.security.cert.Certificate servercert=certs.get(0);
            byte[] bytecert=servercert.getEncoded();
            base64cert=new sun.misc.BASE64Encoder().encode(bytecert);  //给证书编码
            System.out.println(new sun.misc.BASE64Encoder().encode(bytecert));
            PublicKey publicKey=servercert.getPublicKey();
            System.out.println(publicKey);
        }else{
            System.out.println("数据库中不存在此用户！");
        }
        return base64cert;
    }


    public static boolean verifyData(String envelope, String data, String publickKeyB){
        //String KEYSTORE_FILE_A = "E:/PKI/学习阶段成果收集/ejbca证书/2012-11-20/Client.p12";
        String KEYSTORE_FILE_B = "E:/PKI/学习阶段成果收集/ejbca证书/2012-11-20/Server.p12";
        //String KEYSTORE_PASSWORD_A = "Client";
        String KEYSTORE_PASSWORD_B = "Server";
        boolean flag=false;
        try {
            PrivateKey privateKeyB= ReadP12Cert.getPrivateKey(KEYSTORE_FILE_B, KEYSTORE_PASSWORD_B);
            byte[] enveloped_content=decryptPKCS7EnvelopedData(privateKeyB,envelope.getBytes());//解除数字信封之后的内容，应该为数字起那名
            flag = SignedData_Verify(enveloped_content, data);
        } catch (CodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(flag==true){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 解析PKCS#7格式的数字信封
     * @param pPrivateKey
     * @param envelope
     * @return
     * @throws Exception
     */
    private static byte[] decryptPKCS7EnvelopedData(PrivateKey pPrivateKey,byte[] envelope)throws Exception
    {
        ByteArrayInputStream is = new ByteArrayInputStream(envelope);
        ASN1 obj = new ASN1(is);
        ASN1Object asn1 = obj.toASN1Object();
        ContentInfo ci = new ContentInfo(asn1);
        EnvelopedData pEnvelopedData = (EnvelopedData) ci.getContent();// 提取签名值
        System.out.println("----> DECRYPT ENVELOPED DATA START");
        System.out.println("Get recipient info");
        RecipientInfo[]  recipients = pEnvelopedData.getRecipientInfos();
        System.out.println("\nThis message can be decrypted by the owners ofthe following certificates:");
        for (int i=0; i<recipients.length; i++) {
            System.out.println("Recipient: "+(i+1));
            System.out.print(recipients[i].getIssuerAndSerialNumber());
            System.out.print(", Version =" + recipients[i].getVersion());
            System.out.print(", Algorithm =" +
                    recipients[i].getKeyEncryptionAlgorithm().toString());
        }
        System.out.println("");
        // decrypt symmetric content encryption key, e.g.:
        System.out.println("decrypt symmetric encryption key");
        SecretKey secretKey = (SecretKey) recipients[0].decryptKey(pPrivateKey);
        System.out.println("***** !!! ULTIMATIVE DECRYPTION !!!! *****");
        pEnvelopedData.setupCipher(pPrivateKey, 0);
        byte[] content = pEnvelopedData.getContent();
        return content;
    }


    public static boolean SignedData_Verify(byte[] sign, String data)
            throws Exception, CodingException {
        ByteArrayInputStream is = new ByteArrayInputStream(sign);
        ASN1 obj = new ASN1(is);
        ASN1Object asn1 = obj.toASN1Object();
        ContentInfo ci = new ContentInfo(asn1);
        SignedData signedData = (SignedData) ci.getContent();// 提取签名值
        byte[] signed = signedData.getContent();
        String signedstr = byte2hex(signed);
        // 得到前台明文，并进行字符集的处理，以便正确处理中文。
        // String data ="史建宜";// 明文
        // 计算前台明文的SHA1摘要。
        String dataverify = SHA1.hex_sha1(data);
        dataverify = dataverify.toUpperCase();
        // 得到20位的16进制字符串
        byte[] unicodeOfdataverify = dataverify.getBytes("UTF-16LE");
        String unicodeStr = byte2hex(unicodeOfdataverify);
        // 最后比较由密文中提取出的明文摘要与后台重新计算的明文摘要，从而完成数据的校验。
        // 验证证书的有效性
        SignerInfo[] signer_infos = signedData.getSignerInfos();
        for (int i = 0; i < signer_infos.length; i++) {
            try {
                // verify the signed data using the SignerInfo at index i
                X509Certificate signer_cert = signedData.verify(i);
                // if the signature is OK the certificate of the signer is
                // returned
                System.out.println("Signature OK from signer: "
                        + signer_cert.getSubjectDN());
            } catch (SignatureException ex) {
                // if the signature is not OK a SignatureException is thrown
                System.out.println("Signature ERROR from signer: "
                        + signedData.getCertificate(
                        signer_infos[i].getIssuerAndSerialNumber())
                        .getSubjectDN());
            }
        }
        // 数据验证成功
        if (byte2hex(unicodeOfdataverify).equals(
                byte2hex(signedData.getContent()))) {
            System.out.println("数据验证成功！！！");
            return true;
        } else {
            System.out.println("数据验证失败！！！");
            return false;
        }
    }


    // 字节码转换成16进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }


}
