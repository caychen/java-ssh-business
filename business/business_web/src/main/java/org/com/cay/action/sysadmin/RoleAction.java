package org.com.cay.action.sysadmin;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.com.cay.action.BaseAction;
import org.com.cay.entity.Module;
import org.com.cay.entity.Role;
import org.com.cay.service.sysadmin.IModuleService;
import org.com.cay.service.sysadmin.IRoleService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class RoleAction extends BaseAction implements ModelDriven<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Role model = new Role();
	private Page<Role> page = new Page<Role>();

	private IRoleService roleService;
	private IModuleService moduleService;

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public Page<Role> getPage() {
		return page;
	}

	public void setPage(Page<Role> page) {
		this.page = page;
	}

	@Override
	public Role getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public String list() {
		roleService.findPage("from Role", page, Role.class, null);
		page.setUrl("roleAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Dept对象，而page数据在Dept对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() {
		// 根据id得到对象
		Role Role = roleService.get(Role.class, model.getId());
		super.pushObject(Role);
		return "toview";
	}

	public String tocreate() {
		List<Role> roleList = roleService.find("from Role", Role.class, null);
		super.pushObject("roleList", roleList);
		return "tocreate";
	}

	public String insert() {
		roleService.saveOrUpdate(model);
		return "alist";
	}

	public String toupdate() {
		Role Role = roleService.get(Role.class, model.getId());
		super.pushObject(Role);

		// List<Role> roleList = roleService.find("from Role", Role.class,null);
		//
		// // 删除自身作为父部门
		// //deptList.remove(Role);
		//
		// super.pushObject("roleList", roleList);

		return "toupdate";
	}

	public String update() {
		Role Role = roleService.get(Role.class, model.getId());
		Role.setName(model.getName());
		Role.setRemark(model.getRemark());

		roleService.saveOrUpdate(Role);
		return "alist";
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		roleService.delete(Role.class, ids);
		return "alist";
	}

	// 进入模块分配页面
	public String tomodule() {
		// 根据角色id得到角色对象
		Role role = roleService.get(Role.class, model.getId());

		// 保存到值栈中
		super.pushObject(role);

		return "tomodule";
	}

	/*
	 * zTree树的json结构
	 * [{id:'子模块id',pid:'父模块id',name:'模块名',checked:"true|false"},{}]
	 * 
	 * 使用response输出数据
	 */
	public String roleModuleJsonStr() throws IOException {
		Role role = roleService.get(Role.class, model.getId());

		Set<Module> modulesSet = role.getModules();

		// 加载所有的模块
		List<Module> modules = moduleService.find("from Module", Module.class, null);
		int moduleSize = modules.size();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Module module : modules) {
			moduleSize--;

			sb.append("{id:\"").append(module.getId()).append("\",pId:\"").append(module.getParentId())
					.append("\",name:\"").append(module.getName()).append("\",checked:\"");

			if (modulesSet.contains(module)) {
				sb.append("true");
			} else {
				sb.append("false");
			}
			sb.append("\"}");

			if (moduleSize > 0) {
				sb.append(",");
			}
		}

		sb.append("]");

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		response.getWriter().write(sb.toString());
		return NONE;
	}

	private String moduleIds;

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	public String module() {
		Role role = roleService.get(Role.class, model.getId());
		String[] ids = moduleIds.split(",");

		Set<Module> modules = new HashSet<Module>();
		if (ids != null && ids.length > 0) {
			for (String moduleId : ids) {
				Module module = moduleService.get(Module.class, moduleId);
				modules.add(module);
			}

		}
		role.setModules(modules);
		roleService.saveOrUpdate(role);

		return "alist";
	}

}
