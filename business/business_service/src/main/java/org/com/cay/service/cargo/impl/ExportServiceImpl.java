package org.com.cay.service.cargo.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Contract;
import org.com.cay.entity.ContractProduct;
import org.com.cay.entity.Export;
import org.com.cay.entity.ExportProduct;
import org.com.cay.entity.ExtCproduct;
import org.com.cay.entity.ExtEproduct;
import org.com.cay.service.cargo.IExportService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;
import org.springframework.beans.BeanUtils;

public class ExportServiceImpl implements IExportService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Export get(Class<Export> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Export entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增报运单
			entity.setState(0);//状态
			
			String[] ids = entity.getContractIds().split(", ");
			StringBuilder sb = new StringBuilder();
			
			//遍历每一个购销合同的id，得到每个购销合同对象，并修改购销合同的状态为2
			for (String id : ids) {
				Contract contract = baseDao.get(Contract.class, id);
				contract.setState(2);//修改状态
				baseDao.saveOrUpdate(contract);
				
				sb.append(contract.getContractNo()).append(" ");
			}
			
			//设置合同及确认书号
			entity.setCustomerContract(sb.toString());
			entity.setContractIds(UtilFuns.joinStr(ids, ","));
			entity.setInputDate(new Date());
			//通过购销合同的集合，跳跃查询出它下面的货物列表
			String hql = "from ContractProduct where contract.id in (" + UtilFuns.joinInStr(ids) + ")";
			List<ContractProduct> list = baseDao.find(hql, ContractProduct.class, null);
			
			//数据搬家
			Set<ExportProduct> epSet = new HashSet<ExportProduct>();
			for (ContractProduct cp : list) {
				ExportProduct ep = new ExportProduct();//报运单下的商品
				ep.setBoxNum(cp.getBoxNum());
				ep.setCnumber(cp.getCnumber());
				ep.setFactory(cp.getFactory());
				ep.setOrderNo(cp.getOrderNo());
				ep.setPackingUnit(cp.getPackingUnit());
				ep.setPrice(cp.getPrice());
				ep.setProductNo(cp.getProductNo());
				ep.setExport(entity);//设置商品与报运单的关联关系， 多对一
				
				epSet.add(ep);
				
				//加载购销合同下当前货物的附件列表
				Set<ExtCproduct> extCSet = cp.getExtCproducts();
				Set<ExtEproduct> extESet = new HashSet<ExtEproduct>();
				
				for(ExtCproduct extC : extCSet){
					ExtEproduct extE = new ExtEproduct();
					
					//拷贝对象的属性
					BeanUtils.copyProperties(extC, extE);
					/*extE.setAmount(extC.getAmount());
					extE.setCnumber(extC.getCnumber());
					extE.setCreateBy(extC.getCreateBy());
					extE.setCreateDept(extC.getCreateDept());
					extE.setFactory(extC.getFactory());
					extE.setOrderNo(extC.getOrderNo());
					extE.setPackingUnit(extC.getPackingUnit());
					extE.setPrice(extC.getPrice());
					extE.setProductDesc(extC.getProductDesc());
					extE.setProductImage(extC.getProductImage());
					extE.setProductNo(extC.getProductNo());
					extE.setProductRequest(extC.getProductRequest());
					extE.setUpdateBy(extC.getUpdateBy());
					*/
					extE.setId(null);
					
					extE.setExportProduct(ep);//附件与商品，多对一
					
					extESet.add(extE);//向列表中添加元素
				}
				
				//向报运单下的商品对象添加附件
				ep.setExtEproducts(extESet);
			}
			
			//外层循环退出时，设置一个报运单下有多个商品
			entity.setExportProducts(epSet);
			
			
		} else {
			entity.setUpdateTime(new Date());
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Export> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Export> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Export> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

	@Override
	public void changeState(String[] ids, Integer state) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			Export export = baseDao.get(Export.class, id);
			export.setState(state);
			baseDao.saveOrUpdate(export);// 可以不写
		}
	}

	@Override
	public void updateE(Export export2) {
		// TODO Auto-generated method stub
		Export export = baseDao.get(Export.class, export2.getId());
		
		export.setState(export2.getState());
		export.setRemark(export2.getRemark());
		
		Set<ExportProduct> exportProducts = export2.getExportProducts();
		for (ExportProduct ep : exportProducts) {
			ExportProduct exportProduct = baseDao.get(ExportProduct.class, ep.getId());
			exportProduct.setTax(ep.getTax());
			baseDao.saveOrUpdate(exportProduct);
		}
		
		baseDao.saveOrUpdate(export);
	}

}
