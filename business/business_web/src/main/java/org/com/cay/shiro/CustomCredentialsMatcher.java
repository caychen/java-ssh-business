package org.com.cay.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.com.cay.utils.Encrypt;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	// token代表用户在界面上输入的用户名和密码,info代表从数据库中得到的加密数据
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		// TODO Auto-generated method stub
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		// 将用户在界面上输入的密码进行加密
		Object pwd = Encrypt.md5(new String(upToken.getPassword()), upToken.getUsername());

		// 从数据库中获取加密密码
		Object dbPwd = info.getCredentials();

		return this.equals(pwd, dbPwd);
	}

}
