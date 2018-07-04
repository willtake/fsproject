package com.txp.fs.common.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.txp.fs.FSApplication;


@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = FSApplication.class)   // 2
@WebAppConfiguration   // 3
@IntegrationTest("server.port:80") 
public class StockMailSenderTest {
	
	@Autowired
	StockMailSender stockMailSender;

	@Test
	public void testMailSender() {
		//fail("Not yet implemented");

//		stockMailSender.mailSend("testjghhgjjgh", "msg");
	}

}
