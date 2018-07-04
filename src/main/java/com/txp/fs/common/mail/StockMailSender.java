package com.txp.fs.common.mail;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class StockMailSender {
	
	@Autowired 
	JavaMailSender javaMailSender;
	 
	public void mailSend(String title, String msg, int target) throws MessagingException
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		 
		String[] receiverMain = {"willtake@naver.com", "j2yongjin@naver.com", "jongsuk.lee@triplexpartners.com", "siwoon.sung@triplexpartners.com", "hyojin.eom@triplexpartners.com"};
		String[] receiverSub = {"willtake@naver.com", "j2yongjin@naver.com"};
		helper.setSubject(title);
		helper.setText(msg, true);
		helper.setFrom("j2yongjin@naver.com");
		if (target == 0) {
			helper.setTo(receiverMain);
		} else {
			helper.setTo(receiverSub);
		}

		try{
			javaMailSender.send(message);
		}catch(Exception e){
			;
		}
	}
}
