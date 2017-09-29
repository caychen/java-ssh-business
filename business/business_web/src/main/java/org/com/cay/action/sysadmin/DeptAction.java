package org.com.cay.action.sysadmin;

import java.util.List;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.Dept;
import org.com.cay.service.sysadmin.IDeptService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class DeptAction extends BaseAction implements ModelDriven<Dept> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dept model = new Dept();
	private Page<Dept> page = new Page<Dept>();

	// 注入DeptService
	private IDeptService deptService;

	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public Page<Dept> getPage() {
		return page;
	}

	public void setPage(Page<Dept> page) {
		this.page = page;
	}

	@Override
	public Dept getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public String list() {
		deptService.findPage("from Dept", page, Dept.class, null);
		page.setUrl("deptAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Dept对象，而page数据在Dept对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() {
		// 根据id得到对象
		Dept dept = deptService.get(Dept.class, model.getId());
		super.pushObject(dept);
		return "toview";
	}

	public String tocreate() {
		List<Dept> deptList = deptService.find("from Dept where state = ?", Dept.class, new Object[] { 1 });
		super.pushObject("deptList", deptList);
		return "tocreate";
	}

	public String insert() {
		deptService.saveOrUpdate(model);
		return "alist";
	}

	public String toupdate() {
		Dept dept = deptService.get(Dept.class, model.getId());
		super.pushObject(dept);

		List<Dept> deptList = deptService.find("from Dept where state = ?", Dept.class, new Object[] { 1 });

		// 删除自身作为父部门
		deptList.remove(dept);

		super.pushObject("deptList", deptList);

		return "toupdate";
	}

	public String update() {
		Dept dept = deptService.get(Dept.class, model.getId());
		dept.setDeptName(model.getDeptName());
		dept.setParent(model.getParent());

		deptService.saveOrUpdate(dept);
		return "alist";
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		deptService.delete(Dept.class, ids);
		return "alist";
	}

}
