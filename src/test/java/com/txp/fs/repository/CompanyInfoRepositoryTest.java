package com.txp.fs.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.txp.fs.FSApplication;
import com.txp.fs.domain.CompanyInfo;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = FSApplication.class) 
@WebAppConfiguration   // 3
@IntegrationTest("server.port:80") 
public class CompanyInfoRepositoryTest {
	
	@Autowired
	CompanyInfoRepository companyInfoRepository;
	@Test
	public void testFindByCompany() {
		CompanyInfo companyInfo =  companyInfoRepository.findByCompanyCode("217600");
	}
	
	@Test
	@Ignore
	public void testAvg()
	{
		Calendar rightNow = Calendar.getInstance(); // 현재 시간
		//rightNow.add(Calendar.DATE, 3); // 3일 뒤
		rightNow.add(Calendar.DATE, -14); // 거기에 3달 전
		System.out.println(" print11 : ");
		List<CompanyInfo> companyList = companyInfoRepository.findMoveAvg(rightNow.getTime(), new Date());
		
		System.out.println(" print : " + companyList.get(0).getCompanyCode());
	}

}
