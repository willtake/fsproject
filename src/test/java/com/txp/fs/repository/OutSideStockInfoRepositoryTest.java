package com.txp.fs.repository;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.txp.fs.FSApplication;
import com.txp.fs.common.util.CalendarUtil;
import com.txp.fs.domain.OutSideStockInfo;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = FSApplication.class) 
@WebAppConfiguration   // 3
@IntegrationTest("server.port:80") 
public class OutSideStockInfoRepositoryTest {

	@Autowired
	OutSideStockInfoRepository outSideStockInfoRepository;
	
	@Test
	public void testFindByCompanyCodeAndSaveDateAfter() throws ParseException {

		// Given
		OutSideStockInfo insertInfo = new OutSideStockInfo();
		insertInfo.setCompanyCode("1");
		insertInfo.setCompanyName("abc");
		insertInfo.setTodayReplyCnt(12L);
		insertInfo.setSaveDate(new Date());

		// When
		OutSideStockInfo outSideStockInfo = outSideStockInfoRepository.findByCompanyCodeAndSaveDateAfter("1", CalendarUtil.getToday());
		if (outSideStockInfo == null) {
			outSideStockInfoRepository.save(insertInfo);
		} else {
			outSideStockInfo.setTodayReplyCnt(insertInfo.getTodayReplyCnt());
			outSideStockInfo.setSaveDate(insertInfo.getSaveDate());
			outSideStockInfoRepository.save(outSideStockInfo);
		}
		
		// Then
	}

}
