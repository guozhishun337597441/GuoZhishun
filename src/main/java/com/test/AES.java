package com.test;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author gzs
 * @className:AES
 * @Descirption :
 * @createDate 2019/5/21 10:35
 */
public class AES {
    public static void main(String[] args) {
        String s = desEncryption("123456abc","ttt");
        System.out.println(s);
        String str1 = desDecryption(s,"ttt");
        System.out.println(str1);
    }
    public static String desEncryption(String passWord,String key){
        try{
            /*1,生成key*/
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom(key.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            /*转换key*/
            Key keyIn = new SecretKeySpec(bytesKey,"AES");

            /*加密*/
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,keyIn);
            return Hex.encodeHexString(cipher.doFinal(passWord.getBytes()));
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desDecryption(String cipherText,String key){
        try{
            /*生成key*/
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom(key.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            /*转换key*/
            Key keyIn = new SecretKeySpec(bytesKey,"AES");


            /*解密*/
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,keyIn);
            return new String(cipher.doFinal(Hex.decodeHex(cipherText.toCharArray())));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
