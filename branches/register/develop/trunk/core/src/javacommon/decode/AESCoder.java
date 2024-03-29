package javacommon.decode;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * AES安全编码组件
 * @author Administrator
 *
 */
public abstract class AESCoder {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITH="AES";

    /**
     * 加密/解密  /工作模式  /填充模式
     */
    public static final String CIPHER_ALGORITHM="AES";


    /**
     * 转换密钥
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception{
        //实例化DES密钥材料
        SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITH);
        return secretKey;
    }


    /**
     * 解密
     * @param data 代加密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k=toKey(key);
        //实例化
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }


    /**
     * 加密
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k=toKey(key);
        //实例化
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception{
        //实例化
        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITH);
        //AES要求密钥长度为128位，192位或256位
        kg.init(128);
        SecretKey secretKey=kg.generateKey();
        //获取密钥的二进制编码形式
        return secretKey.getEncoded();
    }


}