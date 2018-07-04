package com.txp.fs.task;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.txp.fs.component.CroMainService;

@Component
public class CrawlingWorker {
	
	@Autowired
	CroMainService croMainService;

    @Scheduled(cron = "00 30 23 * * *")
	public void getStockInfo() throws IOException, InterruptedException, Exception {
    	
    	System.out.println("###### Crawling Cron Start ######");
    	croMainService.croExec();	
    }
}
