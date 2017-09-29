package org.com.cay.service.sysadmin.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.com.cay.dao.IBaseDao;
import org.com.cay.entity.User;
import org.com.cay.service.sysadmin.IUserService;
import org.com.cay.utils.Encrypt;
import org.com.cay.utils.Page;
import org.com.cay.utils.SysConstant;
import org.com.cay.utils.UtilFuns;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class UserServiceImpl implements IUserService {

	private IBaseDao baseDao;
	private SimpleMailMessage mailMessage;
	private JavaMailSender mailSender;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public User get(Class<User> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
		// TODO Auto-generated method stub
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		if (UtilFuns.isEmpty(entity.getId())) {
			// 新增
			String id = UUID.randomUUID().toString();
			entity.setId(id);
			entity.getUserInfo().setId(id);
			Date now = new Date();
			entity.setCreateTime(now);
			entity.getUserInfo().setCreateTime(now);

			// 设置默认的用户密码
			entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, entity.getUserName()));

			baseDao.saveOrUpdate(entity);

			// 保存用户和发送邮件不需要在同一个事务里。即使发送邮件失败，也不干扰保存用户功能。
			// 再开启一个新的线程完成邮件的发送功能
			/*
			 * Thread th = new Thread(new Runnable() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * try { MailUtils.sendMessage(new String[] {
			 * entity.getUserInfo().getEmail() }, "新员工入职通知", "欢迎您加入本集团，您的用户名:" +
			 * entity.getUserName() + ",初始密码：" + SysConstant.DEFAULT_PASS); }
			 * catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } } }); th.start();
			 */

			// spring集成javaMail
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mailMessage.setTo(entity.getUserInfo().getEmail());
					mailMessage.setSubject("新员工入职通知");
					mailMessage.setText("欢迎您加入本集团，您的用户名:" +
							 entity.getUserName() + ",初始密码：" + SysConstant.DEFAULT_PASS);
					mailSender.send(mailMessage);
				}
			}).start();

		} else {
			// 修改
			Date now = new Date();
			entity.setUpdateTime(now);
			entity.getUserInfo().setUpdateTime(now);
			baseDao.saveOrUpdate(entity);
		}
		// baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<User> entitys) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<User> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<User> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		for (Serializable id : ids) {
			baseDao.deleteById(entityClass, id);
		}
	}

}
