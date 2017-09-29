package org.com.cay.action.sysadmin;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.Module;
import org.com.cay.service.sysadmin.IModuleService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ModuleAction extends BaseAction implements ModelDriven<Module> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Module model = new Module();
	private Page<Module> page = new Page<Module>();

	private IModuleService moduleService;

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public Page<Module> getPage() {
		return page;
	}

	public void setPage(Page<Module> page) {
		this.page = page;
	}

	@Override
	public Module getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public String list() {
		moduleService.findPage("from Module where state = ?", page, Module.class, new Object[] { 1 });
		page.setUrl("moduleAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Dept对象，而page数据在Dept对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() {
		// 根据id得到对象
		Module Module = moduleService.get(Module.class, model.getId());
		super.pushObject(Module);
		return "toview";
	}

	public String tocreate() {
		/*
		 * List<Module> moduleList = moduleService.find("from Module",
		 * Module.class, null); super.pushObject("moduleList", moduleList);
		 */
		return "tocreate";
	}

	public String insert() {
		moduleService.saveOrUpdate(model);
		return "alist";
	}

	public String toupdate() {
		Module Module = moduleService.get(Module.class, model.getId());
		super.pushObject(Module);

		// List<Module> roleList = moduleService.find("from Module",
		// Module.class,null);

		// 删除自身作为父部门
		// deptList.remove(Module);

		// super.pushObject("roleList", roleList);

		return "toupdate";
	}

	public String update() {
		Module module = moduleService.get(Module.class, model.getId());
		module.setName(model.getName());
		module.setLayerNum(model.getLayerNum());
		module.setCpermission(model.getCpermission());
		module.setCurl(model.getCurl());
		module.setCtype(model.getCtype());
		module.setState(model.getState());
		module.setBelong(model.getBelong());
		module.setCwhich(model.getCwhich());
		module.setRemark(model.getRemark());
		module.setOrderNo(model.getOrderNo());

		moduleService.saveOrUpdate(module);
		return "alist";
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		moduleService.delete(Module.class, ids);
		return "alist";
	}

}
