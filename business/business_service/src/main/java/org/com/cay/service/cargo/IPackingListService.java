package org.com.cay.service.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.com.cay.entity.PackingList;
import org.com.cay.utils.Page;


/**
 * @Description:	PackingListService接口
 * @Author:			传智播客 java学院	传智宋江
 * @Company:		http://java.itcast.cn
 * @CreateDate:		2016-8-15 10:45:58
 */

public interface IPackingListService {

	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params);
	public PackingList get(Class<PackingList> entityClass, Serializable id);
	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass, Object[] params);
	
	
	public void saveOrUpdate(PackingList entity);
	public void saveOrUpdateAll(Collection<PackingList> entitys);
	
	public void deleteById(Class<PackingList> entityClass, Serializable id);
	public void delete(Class<PackingList> entityClass, Serializable[] ids);
}
