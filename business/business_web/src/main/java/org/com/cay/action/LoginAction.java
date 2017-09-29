package org.com.cay.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.com.cay.entity.User;
import org.com.cay.utils.SysConstant;

/**
 * @Description: 登录和退出类
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public String login() throws Exception {

		// SSH传统登录方式
		// if(true){
		// String msg = "登录错误，请重新填写用户名密码!";
		// this.addActionError(msg);
		// throw new Exception(msg);
		// }
		// User user = new User(username, password);
		// User login = userService.login(user);
		// if (login != null) {
		// ActionContext.getContext().getValueStack().push(user);
		// session.put(SysConstant.CURRENT_USER_INFO, login); //记录session
		// return SUCCESS;
		// }
		// return "login";

		try {
			// 1、得到Subject
			Subject subject = SecurityUtils.getSubject();
			// System.out.println(subject.isAuthenticated());
			// if (!subject.isAuthenticated()) {
			if (username == null) {
				return "login";
			}
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);

			subject.login(token);

			// 登录成功时，从shiro中获取用户的登录信息
			User user = (User) subject.getPrincipal();
			//System.out.println(user);
			// 将user放入session
			session.put(SysConstant.CURRENT_USER_INFO, user);

			return SUCCESS;
			// }
		} catch (IncorrectCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.put("errorInfo", "对不起，用户名或密码错误，登录失败！");

		} catch (Exception e) {

		}

		return "login";
	}

	// 退出
	public String logout() {
		session.remove(SysConstant.CURRENT_USER_INFO); // 删除session
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
