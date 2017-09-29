package org.com.cay.test.mail;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class JavaMailTest2 {

	//发送简单邮件
	@Test
	public void testSimpleMailMessage() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
		SimpleMailMessage simpleMailMessage = (SimpleMailMessage) ctx.getBean("mailMessage");

		//设置简单邮件对象的属性
		simpleMailMessage.setSubject("Spring集成JavaMail");
		simpleMailMessage.setText("Hello, spring and javamail");
		simpleMailMessage.setTo("412425870@qq.com");
		
		//得到用于邮件发送的bean
		JavaMailSender mailSender = (JavaMailSender) ctx.getBean("mailSender");
		
		//发生邮件
		mailSender.send(simpleMailMessage);
	}
	
	//发送带有附件的邮件
	@Test
	public void testMimeMailMessage() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
		
		//得到用于邮件发送的bean
		JavaMailSender mailSender = (JavaMailSender) ctx.getBean("mailSender");
		
		//创建一个带有附件和图片的邮件
		MimeMessage message = mailSender.createMimeMessage();		
		
		//
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setFrom("1243505830@qq.com");
		helper.setTo("412425870@qq.com");
		//通过工具类设置主题，内容，图片，附件
		helper.setSubject("这是主题");
		//cid:是固定的，后面的image是自己定义的
		helper.setText("<html>"
				+ "<head></head>"
				+ "<body>"
				+ "<h1>你好!</h1>"
				+ "<a href='http://www.baidu.com'>百度一下</a>"
				+ "<img src=cid:image/>"
				+ "</body>"
				+ "</html>",true);//第二个参数说明内容要解析为html代码
		
		//添加图片
		Resource imgResource = new FileSystemResource("F:\\Code\\Maven\\business\\business_web\\src\\main\\webapp\\tmp\\1.jpg");
		helper.addInline("image", imgResource);//contentId必须与cid:image的image一致
		
		//发送附件
		Resource attachResource = new FileSystemResource("F:\\Code\\Maven\\business\\business_web\\src\\main\\webapp\\tmp\\javamail1_4_4.zip");
		helper.addAttachment("zipFile", attachResource);
		
		mailSender.send(message);
	}
}
