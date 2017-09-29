package org.com.cay.job;

import java.util.Date;

/*
 * 定义了一个任务类
 */
public class MailJob {

	public void send(){
		System.out.println("任务执行完成了：" + new Date());
	}
}
