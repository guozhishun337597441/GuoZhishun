package com.Shiro;

import com.Pojo.Permission;
import com.Pojo.User;
import com.Service.ShiroService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import java.util.List;

public class MyShiroReaml extends AuthorizingRealm {

    private Logger log = Logger.getLogger(MyShiroReaml.class);
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {

        /**
         * 登录之后用于授权, 提供用户信息返回权限信息
         * 流程
         * 1.根据用户user->2.获取角色id->3.根据角色id获取权限permission
         */
        //方法一：获得user对象

        User user=(User)pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取permission
        if(user!=null) {
            List<Permission> permissionsByUser = shiroService.getPermissionsByUser(user);
            if (permissionsByUser.size()!=0) {
                for (Permission p: permissionsByUser) {

                    info.addStringPermission(p.getUrl());
                }
                return info;
            }
        }

        //方法二： 从subject管理器里获取user
//      Subject subject = SecurityUtils.getSubject();
//      User _user = (User) subject.getPrincipal();
//      System.out.println("subject"+_user.getUsername());
        return null;
    }

    /**
     *  验证身证,提供账户信息返回认证信息
     * @param
     * @return
     * @throws AuthenticationException
     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //验证账号密码
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        User user = shiroService.getUserByUserName(token.getUsername());
//        System.out.println("2");
//        //最后的比对需要交给安全管理器
//        //三个参数进行初步的简单认证信息对象的包装
//        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getSimpleName());
//        return info;
//    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        String username = userToken.getUsername();

        User u = shiroService.getUserByUserName(username);

        if(u!=null){
            String passWord = u.getPassword();
            SimpleAuthenticationInfo info = null;
            info = new SimpleAuthenticationInfo(u, passWord, ByteSource.Util.bytes(u.getAccount()+"reg"), getName());
            return info;
        }

        return null;
    }

//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        matcher.setHashAlgorithmName("SHA-256");
//        matcher.setHashIterations(1024);
//        super.setCredentialsMatcher(matcher);
//    }

    private ShiroService shiroService;

    public ShiroService getShiroService() {
        return shiroService;
    }

    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }
}
