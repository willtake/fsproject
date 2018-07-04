package com.txp.fs.component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.txp.fs.common.DataServiceConstants;
import com.txp.fs.domain.CompanyInfo;
import com.txp.fs.domain.OutSideStockInfo;


@Service
public class GetDataServiceImpl implements GetDataService {
	
	private Log m_log = LogFactory.getLog("GetDataServiceImpl");

	@Override
	public OutSideStockInfo getBoardData() throws IOException {
		CroJSoup croJSoup = new CroJSoup();
		croJSoup.getHtmlData(DataServiceConstants.UNLISTED_COMPANY_URL);
		OutSideStockInfo boardDataDomain = new OutSideStockInfo();
		return null;
	}

	@Override
	public List<CompanyInfo> getCompanyInfo() throws Exception {
		List<CompanyInfo> companyDomains =  this.selectUnlistedCompanyInfo();
		return companyDomains;
	}
	
	
	
	private List<CompanyInfo> selectUnlistedCompanyInfo() throws Exception {
		CroJSoup croJSoup = new CroJSoup();
		List<CompanyInfo> companyInfoDomains = new ArrayList<CompanyInfo>();
		
		try{
			for(int k=1; k<30; k++)
			{
				String paging = DataServiceConstants.UNLISTED_COMPANY_URL + k;
				
				Document doc = croJSoup.getHtmlData(paging);		
				Element table = doc.select("table").get(12);			
				Elements tbody = table.select("table").get(2).select("tbody tr");
//				Elements tbody = table.select("table").get(7).select("tbody tr");
				int i = 0;
				for (Element row : tbody)
				{
					if(row.select("td").size() < 2) continue;
					Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
					CompanyInfo companyDomain = new CompanyInfo();
					
					for (int j=0;j<6;j++) {				
						if(j==0) companyDomain.setCompanyType(iterElem.next().text());
						if(j==1) companyDomain.setCompanyCode(iterElem.next().text());
						if(j==2){
							String data = iterElem.next().toString();
							companyDomain.setBoardUrl(data.substring(data.indexOf("href=")+6
									,data.indexOf("class=")-2));
							
							companyDomain.setCompanyName(data.substring(data.indexOf("class=")+ 13
									,data.indexOf("</a>")));
									
						}
						if(j==3) companyDomain.setMarketType(iterElem.next().text());
						if(j==4) companyDomain.setCapital(iterElem.next().text());
						if(j==5) companyDomain.setModifyDate(iterElem.next().text());
					}
					i++;
					if(i > 58) break;
					if(getDateCompare(DataServiceConstants.LIMIT_DATE,companyDomain.getModifyDate().replace(".", "")) 
							>= 0 ) return companyInfoDomains; 
					companyInfoDomains.add(companyDomain);
				}
			}
		}catch(Exception ex)
		{
			m_log.error("[GetDataService] SelectUnlistedCompanyInfo ERROR : " + ex.toString());
			throw new Exception();
		}
		return companyInfoDomains;
		

	}

	@Override
	public int SaveCompanyData(CompanyInfo companyDomain) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	@Override
	public List<OutSideStockInfo> getBoardReplyInfo (List<CompanyInfo> companyDomains) throws Exception {
		CroJSoup croJSoup = new CroJSoup();
		
		List<OutSideStockInfo> boardDataDomains = new ArrayList<OutSideStockInfo>();
		int RequestetryCount=0;
		
		OutSideStockInfo boardDataDomain = null;
		for(int i= 0;i<companyDomains.size();i++){
			
			try {
				boardDataDomain = RequestBoardDetailInfo(
					companyDomains, croJSoup, i);
			} catch(Exception ex) {
				RequestetryCount++;
				if(RequestetryCount < 5)
				{
					i--;
					m_log.error("[GetDataService] getBoardReplyInfo network time-out  url: " 
					+ companyDomains.get(i).getBoardUrl());
				} else {
					m_log.error("[GetDataService] getBoardReplyInfo network time-out Over Count : " 
							+RequestetryCount+ " , url : " 
							+ companyDomains.get(i).getBoardUrl());
				}
			}
			boardDataDomains.add(boardDataDomain);
			Thread.sleep(100);
		}
		
		return boardDataDomains;
	}

	private OutSideStockInfo RequestBoardDetailInfo(List<CompanyInfo> companyDomains, CroJSoup croJSoup, int i)
			throws Exception {
		Document doc;
		String todayReplyCnt;
		OutSideStockInfo boardDataDomain = new OutSideStockInfo();
		System.out.println(" reply url : " + companyDomains.get(i).getBoardUrl());
		CompanyInfo ci = companyDomains.get(i);
		doc = croJSoup.getHtmlData(ci.getBoardUrl());		
		
		todayReplyCnt = doc.toString().substring(
				doc.toString().indexOf("오늘게시물 :") + 7
				,doc.toString().indexOf("오늘게시물 :") + 10);
		
		boardDataDomain.setTodayReplyCnt(Long.parseLong(todayReplyCnt.trim()));
		boardDataDomain.setCompanyCode(companyDomains.get(i).getCompanyCode());
		boardDataDomain.setCompanyName(companyDomains.get(i).getCompanyName());
		boardDataDomain.setSaveDate(new Date());
		return boardDataDomain;
	}
	
	private Long getDateCompare(String dateOrg,String End) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    Date beginDate = formatter.parse(End);
	    Date endDate = formatter.parse(dateOrg);
	 
	    long diff = endDate.getTime() - beginDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	    
    	return diffDays;
	}
}
