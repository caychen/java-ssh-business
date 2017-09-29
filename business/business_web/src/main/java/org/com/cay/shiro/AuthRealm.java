package org.com.cay.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.com.cay.entity.Module;
import org.com.cay.entity.Role;
import org.com.cay.entity.User;
import org.com.cay.service.sysadmin.IUserService;

public class AuthRealm extends AuthorizingRealm {

	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		// TODO Auto-generated method stub
		User user = (User) pc.fromRealm(this.getName()).iterator().next();// 根据realm的名字去找对应的realm

		List<String> permissions = new ArrayList<String>();

		// 将用户的角色及模块(权限)遍历出来，交给shiro管理
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			// 遍历每个角色
			Set<Module> modules = role.getModules();// 得到每个角色下的模块列表
			for (Module module : modules) {
				permissions.add(module.getName());
			}
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		// 调用业务方法
		List<User> users = userService.find("from User where userName = ?", User.class,
				new Object[] { upToken.getUsername() });
		if (users != null && users.size() > 0) {
			User user = users.get(0);

			// 第一个参数为当前对象，在授权的时候需要得到该对象，且第三个参数要跟授权方法的fromRealm参数一致，通过该参数去获取对应的realm
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
			return info;// 此处如果不返回null，就会立即进入密码比较器
		}

		return null;// 如果返回null，则会出异常，需要在外部进行捕获异常
	}

}
