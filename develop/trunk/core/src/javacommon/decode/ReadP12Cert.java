package javacommon.decode;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * 读取证书信息
 * @author Administrator
 *
 */
public class ReadP12Cert {
    /**
     * 获取证书公钥
     * @param key_file
     * @param key_password
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key_file,String key_password) throws Exception{
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(key_file);
        char[] nPassword = null;
        if ((key_password == null) || key_password.trim().equals(""))
        {
            nPassword = null;
        }
        else
        {
            nPassword = key_password.toCharArray();
        }
        ks.load(fis, nPassword);
        fis.close();
        Enumeration  aenum = ks.aliases();
        String keyAlias = null;
        if (aenum.hasMoreElements()) // we are readin just one certificate.
        {
            keyAlias = (String)aenum.nextElement();
            //System.out.println("alias=[" + keyAlias + "]");
        }
        Certificate cert = ks.getCertificate(keyAlias);
        PublicKey pubkey = cert.getPublicKey();
        return pubkey;
    }


    /**
     * 获取证书私钥
     * @param key_file
     * @param key_password
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key_file,String key_password) throws Exception{
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(key_file);
        char[] nPassword = null;
        if ((key_password == null) || key_password.trim().equals(""))
        {
            nPassword = null;
        }
        else
        {
            nPassword = key_password.toCharArray();
        }
        ks.load(fis, nPassword);
        fis.close();
        Enumeration  aenum = ks.aliases();
        String keyAlias = null;
        if (aenum.hasMoreElements()) // we are readin just one certificate.
        {
            keyAlias = (String)aenum.nextElement();
            // System.out.println("alias=[" + keyAlias + "]");
        }
        PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
        return prikey;
    }


    //����
//        public static void main(String[] args)
//        {
//            final String KEYSTORE_FILE     = "E:/PKI/ѧϰ�׶γɹ��ռ�/ejbca֤��/shijianyi.p12";
//            final String KEYSTORE_PASSWORD = "shijianyi";
//            final String KEYSTORE_ALIAS    = "alias";
//
//            try
//            {
//                KeyStore ks = KeyStore.getInstance("PKCS12");
//                FileInputStream fis = new FileInputStream(KEYSTORE_FILE);
//
//                // If the keystore password is empty(""), then we have to set
//                // to null, otherwise it won't work!!!
//                char[] nPassword = null;
//                if ((KEYSTORE_PASSWORD == null) || KEYSTORE_PASSWORD.trim().equals(""))
//                {
//                    nPassword = null;
//                }
//                else
//                {
//                    nPassword = KEYSTORE_PASSWORD.toCharArray();
//                }
//                ks.load(fis, nPassword);
//                fis.close();
//
//                System.out.println("keystore type=" + ks.getType());
//
//                // Now we loop all the aliases, we need the alias to get keys.
//                // It seems that this value is the "Friendly name" field in the
//                // detals tab <-- Certificate window <-- view <-- Certificate
//                // Button <-- Content tab <-- Internet Options <-- Tools menu
//                // In MS IE 6.
//                Enumeration enum = ks.aliases();
//                String keyAlias = null;
//                if (enum.hasMoreElements()) // we are readin just one certificate.
//                {
//                    keyAlias = (String)enum.nextElement();
//                    System.out.println("alias=[" + keyAlias + "]");
//                }
//
//                // Now once we know the alias, we could get the keys.
//                System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
//                PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
//                Certificate cert = ks.getCertificate(keyAlias);
//                PublicKey pubkey = cert.getPublicKey();
//
//                System.out.println("cert class = " + cert.getClass().getName());
//                System.out.println("cert = " + cert);
//                System.out.println("public key = " + pubkey);
//                System.out.println("private key = " + prikey);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
}
