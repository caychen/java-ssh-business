package org.com.cay.service.sysadmin.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Role;
import org.com.cay.service.sysadmin.IRoleService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class RoleServiceImpl implements IRoleService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Role get(Class<Role> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Role entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			/*
			 * String id = UUID.randomUUID().toString(); entity.setId(id);
			 * entity.getUserInfo().setId(id);
			 */
			entity.setCreateTime(new Date());
		} else {
			entity.setUpdateTime(new Date());
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Role> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Role> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Role> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

}
