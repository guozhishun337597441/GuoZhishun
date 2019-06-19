package com.test;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;

/**
 * @author gzs
 * @className:PBE
 * @Descirption :pbe加密解密
 * @createDate 2019/5/21 10:07
 */
public class PBE {
    private static byte[] salt = {-107,127,-121,96,27,-17,-100,56};

    public static void main(String[] args) {
        String passWord = "123456abc";
        String str1 = desEncryption(passWord,"ttt");
        String str2 = desDecryptions(str1,"ttt");
        System.out.println("原密码:"+passWord);
        System.out.println("加密后"+str1);
        System.out.println("解密后"+str2);
    }

    public static String desEncryption(String passWord,String keyString){
        try{
            /*口令与密钥*/
            PBEKeySpec pebKeySpec = new PBEKeySpec(keyString.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pebKeySpec);

            /*加密*/
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt,100);
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE,key,parameterSpec);
            return Hex.encodeHexString(cipher.doFinal(passWord.getBytes()));
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desDecryptions(String cipherText,String keyString){
        try{
            /*口令与密钥*/
            PBEKeySpec pebKeySpec = new PBEKeySpec(keyString.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pebKeySpec);

            /*解密*/
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt,100);
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.DECRYPT_MODE,key,parameterSpec);
            return new String(cipher.doFinal(Hex.decodeHex(cipherText.toCharArray())));
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
