package org.com.cay.action.cargo;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.com.cay.action.BaseAction;
import org.com.cay.entity.Contract;
import org.com.cay.entity.User;
import org.com.cay.print.ContractPrint;
import org.com.cay.service.cargo.IContractService;
import org.com.cay.utils.Page;
import org.com.cay.utils.SysConstant;

import com.opensymphony.xwork2.ModelDriven;

public class ContractAction extends BaseAction implements ModelDriven<Contract> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contract model = new Contract();

	@Override
	public Contract getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private Page<Contract> page = new Page<Contract>();

	private IContractService contractService;

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public Page<Contract> getPage() {
		return page;
	}

	public void setPage(Page<Contract> page) {
		this.page = page;
	}

	public String list() {
		String hql = "from Contract where 1 = 1";
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		int degree = user.getUserInfo().getDegree();
		if(degree == 4){
			//普通员工
			hql += " and createBy = '" + user.getId() + "'";
		}else if(degree == 3){
			//部门经理
			hql += " and createDept = '" + user.getDept().getId() + "'";
		}else if(degree == 2){
			//管理本部门及下属部门
		}else if(degree == 1){
			//副总
		}else if(degree == 0){
			//总经理
		}
		contractService.findPage(hql, page, Contract.class, null);
		page.setUrl("contractAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Contract对象，而page数据在Contract对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() {
		// 根据id得到对象
		Contract contract = contractService.get(Contract.class, model.getId());
		super.pushObject(contract);
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
		//1、加入细粒度权限控制的数据
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		
		model.setCreateBy(user.getId());//设置创建者的id
		model.setCreateDept(user.getDept().getId());//设置创建者所在的部门id
		
		contractService.saveOrUpdate(model);
		return "alist";
	}

	public String toupdate() {
		Contract contract = contractService.get(Contract.class, model.getId());
		super.pushObject(contract);

		// List<Module> roleList = moduleService.find("from Module",
		// Module.class,null);

		// 删除自身作为父部门
		// deptList.remove(Module);

		// super.pushObject("roleList", roleList);

		return "toupdate";
	}

	public String update() {
		Contract contract = contractService.get(Contract.class, model.getId());
		contract.setCustomName(model.getCustomName());
		contract.setPrintStyle(model.getPrintStyle());
		contract.setContractNo(model.getContractNo());
		contract.setOfferor(model.getOfferor());
		contract.setInputBy(model.getInputBy());
		contract.setCheckBy(model.getCheckBy());
		contract.setInspector(model.getInspector());
		contract.setSigningDate(model.getSigningDate());
		contract.setImportNum(model.getImportNum());
		contract.setShipTime(model.getShipTime());
		contract.setTradeTerms(model.getTradeTerms());
		contract.setDeliveryPeriod(model.getDeliveryPeriod());
		contract.setCrequest(model.getCrequest());
		contract.setRemark(model.getRemark());

		contractService.saveOrUpdate(contract);
		return "alist";
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		contractService.delete(Contract.class, ids);
		return "alist";
	}

	// 提交
	public String submit() {
		String[] ids = model.getId().split(",");

		contractService.changeState(ids, 1);
		return "alist";
	}

	// 取消
	public String cancel() {
		String[] ids = model.getId().split(",");

		contractService.changeState(ids, 0);
		return "alist";
	}
	
	public String print() throws Exception{
		Contract contract = contractService.get(Contract.class, model.getId());
		
		//应用程序的根路径
		String path = ServletActionContext.getServletContext().getRealPath("/");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ContractPrint cp = new ContractPrint();
		cp.print(contract, path, response);
		return NONE;
	}

}
