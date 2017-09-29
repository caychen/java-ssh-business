package org.com.cay.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.entity.Export;
import org.com.cay.page.Page;


public interface IExportService {

	public List<Export> find(String hql, Class<Export> entityClass, Object[] params);

	public Export get(Class<Export> entityClass, Serializable id);

	public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params);

	public void saveOrUpdate(Export entity);

	public void saveOrUpdateAll(Collection<Export> entitys);

	public void deleteById(Class<Export> entityClass, Serializable id);

	public void delete(Class<Export> entityClass, Serializable[] ids);
}
