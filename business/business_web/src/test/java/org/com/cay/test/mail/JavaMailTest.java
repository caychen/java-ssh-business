package org.com.cay.test.mail;

import org.com.cay.utils.MailUtils;
import org.junit.Test;

public class JavaMailTest {

	@Test
	public void testJavaMail() throws Exception {
		MailUtils.sendMessage(new String[]{"412425870@qq.com"}, "Hello", "I am Cay!");
	}
}
