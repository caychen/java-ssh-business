package org.com.cay.entity;

import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private Dept dept;// 部门，多对一
	private String userName;// 用户名
	private String password;// 密码
	private Integer state;// 状态
	private UserInfo userInfo;// 用户与用户扩展信息，一对一
	private Set<Role> roles = new HashSet<Role>(0);// 用户与角色，多对多

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", dept=" + dept + ", userName=" + userName + ", password=" + password + ", state="
				+ state + ", userInfo=" + userInfo + ", roles=" + roles + "]";
	}

}
