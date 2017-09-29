package org.com.cay.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	public static void sendMessage(String[] toAddresses, String subject, String content) throws Exception {
		Properties props = new Properties();
		props.load(MailUtils.class.getClassLoader().getResourceAsStream("mail.properties"));
		/*
		 * props.put("mail.transport.protocol", "smtps");// 连接协议
		 * props.put("mail.smtp.host", "smtp.qq.com");// 指定邮件的发送服务器的地址
		 * props.put("mail.smtp.port", 465);// 端口号 
		 * props.put("mail.smtp.auth", "true");// 是否要验证 
		 * props.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接
		 */
		Session session = Session.getInstance(props, null);
		session.setDebug(true);// 启用调试模式,可以在控制台输出smtp协议的应答过程

		// 创建一个MimeMessage格式的邮件
		MimeMessage msg = new MimeMessage(session);

		// 设置发送邮件的地址
		Address fromAddress = new InternetAddress(props.getProperty("mail.fromEmail"));
		msg.setFrom(fromAddress);

		int addrLength = toAddresses.length;
		InternetAddress[] addresses = new InternetAddress[addrLength];
		for (int i = 0; i < addrLength; ++i) {
			addresses[i] = new InternetAddress(toAddresses[i]);
		}

		// 设置接收邮件的地址
		msg.setRecipients(Message.RecipientType.TO, addresses);// 设置接收者的地址

		// 设置邮件的主题
		msg.setSubject(subject, "UTF-8");
		// 设置邮件的内容
		msg.setText(content);

		// 保存邮件
		msg.saveChanges();

		// 得到发送邮件通道的起点
		Transport transport = session.getTransport(props.getProperty("mail.transport.protocol"));

		// 通道连接到服务器上
		transport.connect(props.getProperty("mail.fromEmail"), props.getProperty("mail.fromEmailPassword"));

		// 发送邮件
		transport.sendMessage(msg, msg.getAllRecipients());

		// 关闭通道
		transport.close();

	}
}
