package org.com.cay.service.cargo.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Factory;
import org.com.cay.service.cargo.IFactoryService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class FactoryServiceImpl implements IFactoryService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Factory get(Class<Factory> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Factory entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			// entity.setState(1);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Factory> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Factory> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Factory> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

}
