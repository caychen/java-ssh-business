package org.com.cay.service.stat.impl;

import java.util.List;

import org.com.cay.dao.sql.SqlDao;
import org.com.cay.service.stat.IStatChartService;

public class StatChartServiceImpl implements IStatChartService {

	private SqlDao sqlDao;

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	@Override
	public List<String> executeSQL(String sql) {
		// TODO Auto-generated method stub
		return sqlDao.executeSQL(sql);
	}
	
}
