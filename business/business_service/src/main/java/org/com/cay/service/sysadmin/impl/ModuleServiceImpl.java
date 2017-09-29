package org.com.cay.service.sysadmin.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Module;
import org.com.cay.service.sysadmin.IModuleService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class ModuleServiceImpl implements IModuleService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Module get(Class<Module> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Module> findPage(String hql, Page<Module> page, Class<Module> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Module entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			// entity.setState(1);
			entity.setCreateTime(new Date());
		} else {
			entity.setUpdateTime(new Date());
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Module> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Module> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Module> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

}
