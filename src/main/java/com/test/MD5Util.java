package com.test;

import com.Pojo.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Date;

/**
 * @author gzs
 * @className:MD5Util
 * @Descirption :
 * @createDate 2019/6/17 09:46
 */
public class MD5Util {

    //温馨提示：记得在注册中密码存入数据库前也记得加密哦，提供一个utils方法
//进行shiro加密，返回加密后的结果
    public static String md5(String pass){
        String saltSource = "blog";

        //加密方式
        String hashAlgorithmName = "MD5";

        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, pass, salt, hashIterations);
        String password = result.toString();
        return password;
    }

    public static String test(String passWord){
        //加密方式
        String algorithmName = "MD5";

        //加密的字符串
        String source = "double";

        //盐值，用于和密码混合起来使用
        ByteSource salt = ByteSource.Util.bytes(source);

        //加密的次数，可以进行多次加密操作
        int hashIterations = 1;

        //通过simpleHash来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName,passWord,salt,hashIterations);

        return hash.toString();
    }
}
