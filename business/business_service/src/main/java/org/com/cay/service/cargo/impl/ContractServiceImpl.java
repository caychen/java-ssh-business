package org.com.cay.service.cargo.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Contract;
import org.com.cay.service.cargo.IContractService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class ContractServiceImpl implements IContractService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Contract get(Class<Contract> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Contract entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			entity.setTotalAmount(0.0);// 总金额
			entity.setState(0);// 0：草稿，1：已上报
			entity.setCreateTime(new Date());
		} else {
			entity.setUpdateTime(new Date());
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Contract> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Contract> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Contract> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

	@Override
	public void changeState(Serializable[] ids, int state) {
		// TODO Auto-generated method stub
		//遍历ids，并加载出每个购销合同对象
		for (Serializable id : ids) {
			Contract contract = baseDao.get(Contract.class, id);
			contract.setState(state);
			
			baseDao.saveOrUpdate(contract);
		}
	}

}
