package org.com.cay.action.cargo;

import java.util.List;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.ContractProduct;
import org.com.cay.entity.Factory;
import org.com.cay.service.cargo.IContractProductService;
import org.com.cay.service.cargo.IFactoryService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContractProduct model = new ContractProduct();

	@Override
	public ContractProduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private Page<ContractProduct> page = new Page<ContractProduct>();

	private IContractProductService contractProductService;
	private IFactoryService factoryService;

	public void setContractProductService(IContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}

	public void setFactoryService(IFactoryService factoryService) {
		this.factoryService = factoryService;
	}

	public Page<ContractProduct> getPage() {
		return page;
	}

	public void setPage(Page<ContractProduct> page) {
		this.page = page;
	}

	public String tocreate() {
		// 查询出货物生产厂家
		List<Factory> factoryList = factoryService.find("from Factory where ctype = ? and state = ?", Factory.class,
				new Object[] { "货物", 1 });
		super.pushObject("factoryList", factoryList);// 放入值栈中

		// 查询当前购销合同下的货物列表
		contractProductService.findPage("from ContractProduct where contract.id = ?", page, ContractProduct.class,
				new Object[] { model.getContract().getId() });

		// 设置page的url
		page.setUrl("contractProduct_tocreate.action");

		super.pushObject(page);
		return "tocreate";
	}

	public String insert() {
		contractProductService.saveOrUpdate(model);
		return tocreate();
	}

	public String toupdate() {
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		super.pushObject(contractProduct);

		// 查询出货物生产厂家
		List<Factory> factoryList = factoryService.find("from Factory where ctype = ? and state = ?", Factory.class,
				new Object[] { "货物", 1 });
		super.pushObject("factoryList", factoryList);// 放入值栈中

		return "toupdate";
	}

	public String update() {
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		
		contractProduct.setFactory(model.getFactory());
		contractProduct.setFactoryName(model.getFactoryName());
		contractProduct.setProductNo(model.getProductNo());
		contractProduct.setProductImage(model.getProductImage());
		contractProduct.setCnumber(model.getCnumber());
		contractProduct.setAmount(model.getAmount());
		contractProduct.setPackingUnit(model.getPackingUnit());
		contractProduct.setLoadingRate(model.getLoadingRate());
		contractProduct.setBoxNum(model.getBoxNum());
		contractProduct.setPrice(model.getPrice());
		contractProduct.setOrderNo(model.getOrderNo());
		contractProduct.setProductDesc(model.getProductDesc());
		contractProduct.setProductRequest(model.getProductRequest());
		
		contractProductService.saveOrUpdate(contractProduct);
		return tocreate();
	}

	/**
	 * 删除 <a href=
	 * "contractProductAction_delete.action?id=4028d3db5662dfb4015663208f470002&contract.id=4028d3db5662dfb4015662f0ecbc0001">[删除]</a>
	 * id指货物编号 contract.id：购销合同的id
	 * 
	 * 结论：如果操作子项时，最好同时传递他的所有祖宗
	 */
	public String delete() {
		contractProductService.delete(ContractProduct.class, model);
		return tocreate();
	}
}
