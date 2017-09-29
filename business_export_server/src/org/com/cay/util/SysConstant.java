package org.com.cay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 系统全局常量配置类
 */
public class SysConstant {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(SysConstant.class);

	public static String CURRENT_USER_INFO = "_CURRENT_USER"; // 当前用户session
																// name
	public static String USE_DATABASE = "MySql"; // 使用的数据库 Oracle/SQLServer
	public static String USE_DATABASE_VER = "5.0"; // 使用的数据库版本 10g/2000

	public static String DEFAULT_PASS = "123456"; // 默认密码
	public static int PAGE_SIZE = 10; // 分页时一页显示多少条记录
}
