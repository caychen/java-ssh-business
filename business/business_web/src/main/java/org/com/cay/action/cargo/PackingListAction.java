package org.com.cay.action.cargo;

import org.com.cay.action.BaseAction;
import org.com.cay.entity.PackingList;
import org.com.cay.service.cargo.IPackingListService;
import org.com.cay.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 注入service
	private IPackingListService packingListService;

	public void setPackingListService(IPackingListService packingListService) {
		this.packingListService = packingListService;
	}

	// model驱动
	private PackingList model = new PackingList();

	public PackingList getModel() {
		return this.model;
	}

	// 作为属性驱动，接收并封装页面参数
	private Page<PackingList> page = new Page<PackingList>(); // 封装页面的参数，主要当前页参数

	public void setPage(Page<PackingList> page) {
		this.page = page;
	}

	// 列表展示
	public String list() {
		String hql = "from PackingList o"; // 查询所有内容
		// 给页面提供分页数据
		packingListService.findPage(hql, page, PackingList.class, null);
		page.setUrl("packingListAction_list"); // 配置分页按钮的转向链接
		super.pushObject(page);

		return "plist"; // page list
	}

	// 转向新增页面
	public String tocreate() {
		// 准备数据

		return "pcreate";
	}

	// 新增保存
	public String insert() {
		packingListService.saveOrUpdate(model);

		return "alist"; // 返回列表，重定向action_list
	}

	// 转向修改页面
	public String toupdate() {
		// 准备数据

		// 准备修改的数据
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		super.pushObject(obj);

		return "pupdate";
	}

	// 修改保存
	public String update() {
		PackingList packingList = packingListService.get(PackingList.class, model.getId());

		// 设置修改的属性，根据业务去掉自动生成多余的属性
		packingList.setSeller(model.getSeller());
		packingList.setBuyer(model.getBuyer());
		packingList.setInvoiceNo(model.getInvoiceNo());
		packingList.setInvoiceDate(model.getInvoiceDate());
		packingList.setMarks(model.getMarks());
		packingList.setDescriptions(model.getDescriptions());
		packingList.setExportIds(model.getExportIds());
		packingList.setExportNos(model.getExportNos());
		packingList.setState(model.getState());
		packingList.setCreateBy(model.getCreateBy());
		packingList.setCreateDept(model.getCreateDept());
		packingList.setCreateTime(model.getCreateTime());

		packingListService.saveOrUpdate(packingList);

		return "alist";
	}

	// 删除一条
	public String deleteById() {
		packingListService.deleteById(PackingList.class, model.getId());

		return "alist";
	}

	// 删除多条
	public String delete() {
		packingListService.delete(PackingList.class, model.getId().split(", "));

		return "alist";
	}

	// 查看
	public String toview() {
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		super.pushObject(obj);

		return "pview"; // 转向查看页面
	}
}
