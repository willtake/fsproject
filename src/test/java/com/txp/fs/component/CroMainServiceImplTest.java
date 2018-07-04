package com.txp.fs.component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.txp.fs.FSApplication;
import com.txp.fs.common.mail.StockMailSender;
import com.txp.fs.domain.model.CompanyInfoModel;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = FSApplication.class) 
@WebAppConfiguration   // 3
@IntegrationTest("server.port:80") 
public class CroMainServiceImplTest {
	
	@Autowired
	CroMainService croMainService;
	
	@Autowired
	StockMailSender stockMailSender;
	

	@Test
	public void testExec() throws IOException, InterruptedException, Exception {
		croMainService.croExec();
	}
	
	@Test
	public void testMailContents() throws Exception {
		// Given
		String mailContents = croMainService.mailContents();
		
		stockMailSender.mailSend("Tron38X - Noti Mail", mailContents, 1);
	}

	@Test
	public void getMovingAverageCountTest() {

		for(int i= 0; i<500; i++) {
			// Given
			List<BigDecimal> list = new ArrayList<BigDecimal>();
			// 12, 38, 10, 9, 93
			list.add(new BigDecimal(12));
			list.add(new BigDecimal(38));
			list.add(new BigDecimal(10));
			list.add(new BigDecimal(9));
			list.add(new BigDecimal(93));
			
			// When
			Long result = croMainService.getMovingAverageCount(list, list.size());
			System.out.println(result);
		}
	}
	
	@Test
	public void getFiveDaysAverageCountTest() throws Exception {
		Long count = croMainService.getFiveDaysAverageCount("064540");
		System.out.println(count);
	}
	
	@Test
	public void getCompnayNoticeInfoTest() throws Exception {
		
		List<CompanyInfoModel> models = croMainService.getCompnayNoticeInfo();
		for (CompanyInfoModel model : models) {
			System.out.println(model.getCompanyCode() +"	"+ model.getCompanyName() +"	"+ model.getTodayReplyCnt() +"	"+ model.getFiveDaysAverageCnt()  +"	"+ model.getTwoWeeksAverageCnt());
		}
	}
	
}
