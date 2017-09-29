package org.com.cay.service.sysadmin.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Dept;
import org.com.cay.service.sysadmin.IDeptService;
import org.com.cay.utils.Page;
import org.com.cay.utils.UtilFuns;

public class DeptServiceImpl implements IDeptService {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Dept get(Class<Dept> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Dept entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			entity.setState(1);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Dept> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Dept> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		// 有哪些子部门的父部门是该id
		String hql = "from Dept where parent.id = ?";
		List<Dept> findChild = baseDao.find(hql, Dept.class, new Object[] { id });
		if (findChild != null && findChild.size() > 0) {
			for (Dept dept : findChild) {
				deleteById(Dept.class, dept.getId());// 递归调用
			}
		}
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Dept> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

}
