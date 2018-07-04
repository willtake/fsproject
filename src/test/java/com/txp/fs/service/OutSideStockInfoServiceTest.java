package com.txp.fs.service;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.txp.fs.FSApplication;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = FSApplication.class) 
@WebAppConfiguration   // 3
@IntegrationTest("server.port:80") 
public class OutSideStockInfoServiceTest {
	
	@Autowired
	private OutSideStockInfoService outSideStockInfoService;
	
	@Test
	public void deleteBeforeOneMonthDataTest() {
		// Given
		
		// When
		try {
			int result = outSideStockInfoService.deleteBeforeOneMonthData();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Then
	}
	
}