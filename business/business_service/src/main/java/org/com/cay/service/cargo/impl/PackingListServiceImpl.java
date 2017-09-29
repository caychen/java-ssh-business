package org.com.cay.service.cargo.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.PackingList;
import org.com.cay.service.cargo.IPackingListService;
import org.com.cay.utils.Page;

public class PackingListServiceImpl implements IPackingListService {
	// spring注入dao
	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params) {
		return baseDao.find(hql, PackingList.class, params);
	}

	public PackingList get(Class<PackingList> entityClass, Serializable id) {
		return baseDao.get(PackingList.class, id);
	}

	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, PackingList.class, params);
	}

	public void saveOrUpdate(PackingList entity) {
		if (entity.getId() == null) { // 代表新增
			// entity.setState(1); //状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<PackingList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<PackingList> entityClass, Serializable id) {
		baseDao.deleteById(PackingList.class, id);
	}

	public void delete(Class<PackingList> entityClass, Serializable[] ids) {
		baseDao.delete(PackingList.class, ids);
	}

}
