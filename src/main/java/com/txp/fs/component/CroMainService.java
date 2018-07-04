package com.txp.fs.component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.txp.fs.domain.model.CompanyInfoModel;

@Service
public interface CroMainService {
	
	public int croExec() throws IOException, InterruptedException, Exception;
	
	public String mailContents() throws Exception;

	public Long getMovingAverageCount(List<BigDecimal> list, int listSize);
	
	public List<CompanyInfoModel> getCompnayNoticeInfo() throws Exception;
	
	public Long getFiveDaysAverageCount(String companyCode) throws Exception;
	
	public Long getTwoWeeksAverageCount(String companyCode) throws Exception;
}