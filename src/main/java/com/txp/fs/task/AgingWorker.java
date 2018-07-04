package com.txp.fs.task;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.txp.fs.service.OutSideStockInfoService;

@Component
public class AgingWorker {

	@Autowired
	private OutSideStockInfoService outSideStockInfoService;

	@Scheduled(cron = "00 30 03 * * *")
	public void agingOutSideStockInfo() throws ParseException {
		outSideStockInfoService.deleteBeforeOneMonthData();
	}
}
