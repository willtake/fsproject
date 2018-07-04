package com.txp.fs.component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.txp.fs.domain.CompanyInfo;
import com.txp.fs.domain.OutSideStockInfo;


@Service
public interface GetDataService {

	public List<CompanyInfo> getCompanyInfo() throws IOException, ParseException, Exception;
	
	public  OutSideStockInfo getBoardData() throws IOException;
	
	public int SaveCompanyData(CompanyInfo companyDomain);
	
	public List<OutSideStockInfo> getBoardReplyInfo(List<CompanyInfo> companyDomains) throws IOException, InterruptedException, Exception;
	
	
}