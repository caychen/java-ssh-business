package org.com.cay.action.cargo;

import java.util.HashSet;
import java.util.Set;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.Contract;
import org.com.cay.entity.Export;
import org.com.cay.entity.ExportProduct;
import org.com.cay.entity.User;
import org.com.cay.service.cargo.IContractService;
import org.com.cay.service.cargo.IExportProductService;
import org.com.cay.service.cargo.IExportService;
import org.com.cay.utils.Page;
import org.com.cay.utils.SysConstant;
import org.com.cay.utils.UtilFuns;
import org.com.cay.webservice.impl.IExportWebService;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

public class ExportAction extends BaseAction implements ModelDriven<Export> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Export model = new Export();

	@Override
	public Export getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private Page page = new Page();

	private IExportService exportService;
	private IContractService contractService;
	private IExportProductService exportProductService;
	
	
	public void setExportProductService(IExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	private String mr_changed[];
	private String mr_id[];
	private Integer mr_cnumber[];
	private Double mr_grossWeight[];
	private Double mr_netWeight[];
	private Double mr_sizeLength[];
	private Double mr_sizeWidth[];
	private Double mr_sizeHeight[];
	private Double mr_exPrice[];
	private Double mr_tax[];
	
	

	public void setMr_changed(String[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}

	public String list() {
		String hql = "from Export where 1 = 1";
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
		exportService.findPage(hql, page, Export.class, null);
		page.setUrl("exportAction_list");

		// jsp默认先从栈顶取元素
		// 由于栈顶元素现在是ModelDriven的Contract对象，而page数据在Contract对象的下面，所以可以考虑将page压入栈顶
		// 放入栈顶，读取速度快
		super.pushObject(page);
		return "list";
	}

	public String toview() {
		// 根据id得到对象
		Export export = exportService.get(Export.class, model.getId());
		super.pushObject(export);
		return "toview";
	}

	public String tocreate() {
		/*
		 * List<Module> moduleList = moduleService.find("from Module",
		 * Module.class, null); super.pushObject("moduleList", moduleList);
		 */
		return "tocreate";
	}

	/**
	 * 保存
       <input type="hidden" name="contractIds" value="4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024, 4028817a33812ffd0133821a8eb5002b" />
       model对象    ：Export类型
           id:
           contractIds:4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024, 4028817a33812ffd0133821a8eb5002b
	 */
	public String insert() {
		//1、加入细粒度权限控制的数据
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		
		model.setCreateBy(user.getId());//设置创建者的id
		model.setCreateDept(user.getDept().getId());//设置创建者所在的部门id
		
		exportService.saveOrUpdate(model);
		return "alist";
	}

	public String toupdate() {
		Export export = exportService.get(Export.class, model.getId());
		super.pushObject(export);

		//function addTRRecord(objId, id, productNo, cnumber, grossWeight, netWeight, sizeLength, sizeWidth, sizeHeight, exPrice, tax) {
		
		//将前台需要执行的js语句在action中先拼接好后传给前台
		StringBuilder sb = new StringBuilder();
		Set<ExportProduct> exportProducts = export.getExportProducts();
		
		for (ExportProduct ep : exportProducts) {
			sb.append("addTRRecord(\"tbbody\", \"").append(ep.getId());
			sb.append("\",\"").append(ep.getProductNo());
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getCnumber()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getGrossWeight()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getNetWeight()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getSizeLength()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getSizeWidth()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getSizeHeight()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getPrice()));
			sb.append("\",\"").append(UtilFuns.convertNull(ep.getTax()));
			sb.append("\");");
		}
		super.pushObject("mRecordData", sb.toString());
		
		return "toupdate";
	}

	public String update() {
		Export export = exportService.get(Export.class, model.getId());
		
		export.setInputDate(model.getInputDate());
		export.setLcno(model.getLcno());
		export.setConsignee(model.getConsignee());
		export.setShipmentPort(model.getShipmentPort());
		export.setDestinationPort(model.getDestinationPort());
		export.setTransportMode(model.getTransportMode());
		export.setPriceCondition(model.getPriceCondition());
		export.setMarks(model.getMarks());
		export.setRemark(model.getRemark());
		
		Set<ExportProduct> epSet = new HashSet<ExportProduct>();
		
		for(int i = 0; i < mr_id.length; ++i){
			//遍历
			ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
			
			if("1".equals(mr_changed[i])){
				ep.setCnumber(mr_cnumber[i]);
        		ep.setGrossWeight(mr_grossWeight[i]);
        		ep.setNetWeight(mr_netWeight[i]);
        		ep.setSizeLength(mr_sizeLength[i]);
        		ep.setSizeWidth(mr_sizeWidth[i]);
        		ep.setSizeHeight(mr_sizeHeight[i]);
        		ep.setExPrice(mr_exPrice[i]);
        		ep.setTax(mr_tax[i]);
			}
			
			epSet.add(ep);
		}
		export.setExportProducts(epSet);

		exportService.saveOrUpdate(export);
		return "alist";
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		exportService.delete(Export.class, ids);
		return "alist";
	}
	
	// 提交
	public String submit() {
		String[] ids = model.getId().split(",");

		exportService.changeState(ids, 1);
		return "alist";
	}

	// 取消
	public String cancel() {
		String[] ids = model.getId().split(",");

		exportService.changeState(ids, 0);
		return "alist";
	}
	
	
	//查询状态为1的购销合同
	public String contractList(){
		contractService.findPage("from Contract where state = 1", page, Contract.class, null);
		page.setUrl("exportAction_contractList");
		//super.pushObject("contractList", contractList);
		super.pushObject(page);
		return "contractList";
	}
	
	private IExportWebService exportWebService;
	
	public void setExportWebService(IExportWebService exportWebService) {
		this.exportWebService = exportWebService;
	}

	public String export() throws Exception{
		//1、确定选中的报运单的对象
		Export export = exportService.get(Export.class, model.getId());
		
		//2、将对象转成json串
		String exportJsonString = JSON.toJSONString(export);
		System.out.println(exportJsonString);
		
		//3、调用远程webservice服务，将json串传递给webservice
		String result = exportWebService.exportE(exportJsonString);
		System.out.println(result);
		
		//4、处理webservice响应结果
		Export export2 = JSON.parseObject(result, Export.class);
		exportService.updateE(export2);
		
		return "alist";
	}

}
