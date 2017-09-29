package org.com.cay.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.Dept;
import org.com.cay.entity.Role;
import org.com.cay.entity.User;
import org.com.cay.exception.SysException;
import org.com.cay.service.sysadmin.IDeptService;
import org.com.cay.service.sysadmin.IRoleService;
import org.com.cay.service.sysadmin.IUserService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User model = new User();
	private Page<User> page = new Page<User>();

	private IUserService userService;
	private IDeptService deptService;
	private IRoleService roleService;

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Page<User> getPage() {
		return page;
	}

	public void setPage(Page<User> page) {
		this.page = page;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public String list() {
		userService.findPage("from User", page, User.class, null);
		page.setUrl("userAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Dept对象，而page数据在Dept对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() throws Exception {
		// 根据id得到对象
		try {
			User User = userService.get(User.class, model.getId());
			super.pushObject(User);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SysException("对不起，请先选择！");
		}
		return "toview";
	}

	public String tocreate() {
		List<User> userList = userService.find("from User where state = ?", User.class, new Object[] { 1 });
		super.pushObject("userList", userList);

		List<Dept> deptList = deptService.find("from Dept where state = ?", Dept.class, new Object[] { 1 });
		super.pushObject("deptList", deptList);
		return "tocreate";
	}

	public String insert() {
		userService.saveOrUpdate(model);
		return "alist";
	}

	public String toupdate() {
		User User = userService.get(User.class, model.getId());
		super.pushObject(User);

		List<User> deptList = userService.find("from Dept where state = ?", User.class, new Object[] { 1 });

		// 删除自身作为父部门
		deptList.remove(User);

		super.pushObject("deptList", deptList);

		return "toupdate";
	}

	public String update() {
		User user = userService.get(User.class, model.getId());
		user.setDept(model.getDept());
		user.setUserName(model.getUserName());
		user.setState(model.getState());
		userService.saveOrUpdate(user);
		return "alist";
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		userService.delete(User.class, ids);
		return "alist";
	}

	public String torole() {
		// 根据id获取对象
		User user = userService.get(User.class, model.getId());

		// 将对象保存到值栈中
		super.pushObject(user);

		// 获取所有的角色列表
		List<Role> roles = roleService.find("from Role", Role.class, null);

		// 将roles放入值栈中
		super.pushObject("roleList", roles);

		// 得到当前用户拥有的角色
		Set<Role> roleSet = user.getRoles();
		StringBuilder sb = new StringBuilder();
		for (Role role : roleSet) {
			sb.append(role.getName()).append(",");
		}

		// 当前用户的角色字符串放入值栈
		super.pushObject("userRoleStr", sb.toString());

		return "torole";
	}

	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String role() {
		User user = userService.get(User.class, model.getId());
		Set<Role> roles = new HashSet<Role>();
		for (String roleId : roleIds) {
			Role role = roleService.get(Role.class, roleId);
			roles.add(role);
		}
		user.setRoles(roles);
		userService.saveOrUpdate(user);
		return "alist";
	}

}
