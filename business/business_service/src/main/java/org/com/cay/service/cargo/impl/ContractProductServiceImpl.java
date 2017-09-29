package org.com.cay.service.cargo.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Contract;
import org.com.cay.entity.ContractProduct;
import org.com.cay.entity.ExtCproduct;
import org.com.cay.service.cargo.IContractProductService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class ContractProductServiceImpl implements IContractProductService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass,
			Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ContractProduct entity) {
		// TODO Auto-generated method stub
		double amount = 0.0;
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
				amount = entity.getPrice() * entity.getCnumber();// 货物总金额
				entity.setAmount(amount);
			}

			// 修改购销合同的总金额
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
			contract.setTotalAmount(contract.getTotalAmount() + amount);

			// 保存
			baseDao.saveOrUpdate(contract);
		} else {
			// 修改
			double oldAmount = entity.getAmount();
			if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
				amount = entity.getPrice() * entity.getCnumber();// 货物总金额
				entity.setAmount(amount);
			}

			// 修改购销合同的总金额
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
			contract.setTotalAmount(contract.getTotalAmount() - oldAmount + amount);

			// 保存
			baseDao.saveOrUpdate(contract);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

	@Override
	public void delete(Class<ContractProduct> entityClass, ContractProduct model) {
		// TODO Auto-generated method stub
		//1、加载要删除的货物对象
		ContractProduct contractProduct = baseDao.get(ContractProduct.class, model.getId());
		
		//2、通过关联级别的数据加载，得到当前货物下的所有附件列表
		Set<ExtCproduct> extCproductSet = contractProduct.getExtCproducts();
		
		//3、加载购销合同对象
		Contract contract = baseDao.get(Contract.class, model.getContract().getId());
		
		//4、遍历附件列表,先减去附件总价
		for (ExtCproduct extCproduct : extCproductSet) {
			contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
		}
		
		//5、购销合同总金额-货物总金额
		contract.setTotalAmount(contract.getTotalAmount() - contractProduct.getAmount());
		
		//6、更新购销合同的总金额
		baseDao.saveOrUpdate(contract);
		
		//7、删除，级联删除附件  cascade="all"
		baseDao.deleteById(ContractProduct.class, model.getId());
	}

}
