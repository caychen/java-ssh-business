package org.com.cay.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.Export;
import org.com.cay.page.Page;
import org.com.cay.service.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: ExportService接口
 */

@Service
public class ExportServiceImpl implements IExportService {
	
	@Autowired
	private IBaseDao baseDao;

/*	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
*/
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
		return baseDao.find(hql, Export.class, params);
	}

	public Export get(Class<Export> entityClass, Serializable id) {
		return baseDao.get(Export.class, id);
	}

	public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Export.class, params);
	}

	public void saveOrUpdate(Export entity) {
		if (entity.getId() == null) { // 代表新增
			entity.setState(1); // 状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<Export> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Export> entityClass, Serializable id) {
		baseDao.deleteById(Export.class, id);
	}

	public void delete(Class<Export> entityClass, Serializable[] ids) {
		baseDao.delete(Export.class, ids);
	}

}
