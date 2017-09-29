package org.com.cay.service.cargo.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Contract;
import org.com.cay.entity.ExtCproduct;
import org.com.cay.service.cargo.IExtCproductService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class ExtCproductServiceImpl implements IExtCproductService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass,
			Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExtCproduct entity) {
		// TODO Auto-generated method stub
		double amount = 0.0;
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
				amount = entity.getPrice() * entity.getCnumber();// 货物总金额
				entity.setAmount(amount);
			}

			// 修改购销合同的总金额
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());// 根据购销合同的id,得到购销合同对象
			contract.setTotalAmount(contract.getTotalAmount() + amount);

			// 保存购销合同的总金额
			baseDao.saveOrUpdate(contract);

		} else {
			// 修改
			double oldAmount = entity.getAmount();// 取出货物的原有总金额
			if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
				amount = entity.getPrice() * entity.getCnumber();// 货物总金额
				entity.setAmount(amount);
			}

			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());// 根据购销合同的id,得到购销合同对象
			contract.setTotalAmount(contract.getTotalAmount() - oldAmount + amount);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

	@Override
	public void delete(Class<ExtCproduct> entityClass, ExtCproduct model) {
		// TODO Auto-generated method stub
		ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, model.getId());//得到附件对象
		
		Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());//得到购销合同对象
		
		//修改购销合同的总金额 
		contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
		
		//保存总金额 
		baseDao.saveOrUpdate(contract);
		
		//删除附件
		baseDao.deleteById(ExtCproduct.class,model.getId());
	}

}
