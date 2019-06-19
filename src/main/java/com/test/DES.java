package com.test;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author gzs
 * @className:DES
 * @Descirption :DES加密解密
 * @createDate 2019/5/21 09:28
 */
public class DES {
    public static void main(String[] args) {
        String s = desEncryption("123456abc","ttt");
        System.out.println(s);
        String str1 = desDecryption(s,"ttt");
        System.out.println(str1);
    }

    /**
     * des加密
     * @param passWord
     * @param key 钥
     * @return
     */
    public static String desEncryption(String passWord,String key){
        try{
            /*1,生成key*/
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESEde");
            keyGenerator.init(new SecureRandom(key.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            /*转换key*/
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESEde");
            Key convertKey = factory.generateSecret(deSedeKeySpec);

            /*加密*/
            Cipher cipher = Cipher.getInstance("DESEde");
            cipher.init(Cipher.ENCRYPT_MODE,convertKey);
            return Hex.encodeHexString(cipher.doFinal(passWord.getBytes()));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * des解密
     * @param cipherText
     * @param key
     * @return
     */
    public static String desDecryption(String cipherText,String key){
        try{
            /*生成key*/
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESEde");
            keyGenerator.init(new SecureRandom(key.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            /*转换key*/
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESEde");
            Key convertKey = factory.generateSecret(deSedeKeySpec);

            /*解密*/
            Cipher cipher = Cipher.getInstance("DESEde");
            cipher.init(Cipher.DECRYPT_MODE,convertKey);
            return new String(cipher.doFinal(Hex.decodeHex(cipherText.toCharArray())));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
