package com.test;

import com.Pojo.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gzs
 * @className:MD5Test
 * @Descirption :
 * @createDate 2019/5/21 08:40
 */
public class MD5Test {
    public static Map<String,User> userMap = new HashMap<String, User>();
    static{
        userMap.put("zs",new User("zs","123456"));
        userMap.put("ls",new User("ls","123456"));
        userMap.put("ww",new User("ww","123456"));
    }

    public static void main(String[] args) {
        String passWord ="1234";
        /*盐量随机*/
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encodedPassword= new SimpleHash("md5",passWord,"1234",2).toString();
        System.out.println("原密码:"+passWord);
        System.out.println("盐:"+salt);
        System.out.println("加密后的密码:"+encodedPassword);
        System.out.println(ByteSource.Util.bytes("1234"));
        /*加密*/
        /**
         * @param md5 加密方式
         * @param encodePassWord 密码
         * @param salt 盐
         * @param hashIterations 循环次数
         */
        String passwordEncoded = new SimpleHash("md5",passWord, ByteSource.Util.bytes("123"),1024).toString();
        System.out.println("解密后的密码:"+passwordEncoded);


    }
}
