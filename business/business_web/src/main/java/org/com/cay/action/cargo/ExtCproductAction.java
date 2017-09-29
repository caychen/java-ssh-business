package org.com.cay.action.cargo;

import java.util.List;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.ExtCproduct;
import org.com.cay.entity.Factory;
import org.com.cay.service.cargo.IExtCproductService;
import org.com.cay.service.cargo.IFactoryService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExtCproduct model = new ExtCproduct();

	@Override
	public ExtCproduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private IExtCproductService extCproductService;
	private IFactoryService factoryService;

	public void setFactoryService(IFactoryService factoryService) {
		this.factoryService = factoryService;
	}

	public void setExtCproductService(IExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}

	private Page<ExtCproduct> page = new Page<ExtCproduct>();

	public Page<ExtCproduct> getPage() {
		return page;
	}

	public void setPage(Page<ExtCproduct> page) {
		this.page = page;
	}

	public String list() {
		extCproductService.findPage("from ExtCproduct", page, ExtCproduct.class, null);
		page.setUrl("extCproductAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Contract对象，而page数据在Contract对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() {
		// 根据id得到对象
		ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());
		super.pushObject(extCproduct);
		return "toview";
	}

	public String tocreate() {
		// 查询出货物生产厂家
		List<Factory> factoryList = factoryService.find("from Factory where ctype = ? and state = ?", Factory.class,
				new Object[] { "附件", 1 });
		super.pushObject("factoryList", factoryList);// 放入值栈中

		// 查询当前货物下的附件列表
		extCproductService.findPage("from ExtCproduct where contractProduct.id = ?", page, ExtCproduct.class,
				new Object[] { model.getContractProduct().getId() });

		// 设置page的url
		page.setUrl("extCproduct_tocreate.action");

		super.pushObject(page);
		return "tocreate";
	}

	public String insert() {
		extCproductService.saveOrUpdate(model);
		return tocreate();
	}

	public String toupdate() {
		ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());
		super.pushObject(extCproduct);

		// 查询出货物生产厂家
		List<Factory> factoryList = factoryService.find("from Factory where ctype = ? and state = ?", Factory.class,
				new Object[] { "附件", 1 });
		super.pushObject("factoryList", factoryList);// 放入值栈中

		return "toupdate";
	}

	public String update() {
		ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());

		extCproduct.setFactory(model.getFactory());
		extCproduct.setFactoryName(model.getFactoryName());
		extCproduct.setProductNo(model.getProductNo());
		extCproduct.setProductImage(model.getProductImage());
		extCproduct.setCnumber(model.getCnumber());
		extCproduct.setAmount(model.getAmount());
		extCproduct.setPackingUnit(model.getPackingUnit());
		extCproduct.setPrice(model.getPrice());
		extCproduct.setOrderNo(model.getOrderNo());
		extCproduct.setProductDesc(model.getProductDesc());
		extCproduct.setProductRequest(model.getProductRequest());

		extCproductService.saveOrUpdate(extCproduct);
		return tocreate();
	}

	public String delete() {
		extCproductService.delete(ExtCproduct.class, model);
		return tocreate();
	}
}
