package com.txp.fs.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.txp.fs.common.mail.StockMailSender;
import com.txp.fs.common.util.CalendarUtil;
import com.txp.fs.domain.CompanyInfo;
import com.txp.fs.domain.OutSideStockInfo;
import com.txp.fs.domain.model.CompanyInfoModel;
import com.txp.fs.repository.CompanyInfoRepository;
import com.txp.fs.repository.OutSideStockInfoRepository;


@Service
public class CroMainServiceImpl implements CroMainService {
	
	@Autowired
	StockMailSender stockMailSender;
	
	@Autowired
	private VelocityEngine engine;
	
	@Autowired
	CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	OutSideStockInfoRepository outSideStockInfoRepository;
	
	private Log m_log = LogFactory.getLog("CroMainServiceImpl");

	@Override
	public int croExec() throws Exception {
		// TODO Auto-generated method stub
		
		
		 m_log.info("CroMain Service START!");
		 
		 try{
		     GetDataService getDataService = new GetDataServiceImpl(); 
		     m_log.info("[Cro MAIN] GET COMPANY INFO crawling ");
		     List<CompanyInfo> companyDomains =  getDataService.getCompanyInfo();
		     m_log.info("[Cro MAIN] GET COMPANY INFO crawling  SUCCESS COMPANY COUNT : " + companyDomains.size());
		     for(int i=0;i<companyDomains.size();i++)
		     {
		    	 CompanyInfo companyInfo = companyInfoRepository.findByCompanyCode(companyDomains.get(i).getCompanyCode());
		    	 
		    	 if(companyInfo == null)
		    	 {
		    		 m_log.info("SAVE COMPANY NAME : " + companyDomains.get(i).getCompanyName());
		    		 companyInfoRepository.save(companyDomains.get(i));
		    	 }
		     }
		    
			 // 3. select reply count
		     m_log.info("[Cro MAIN] GET COMPANY REPLY COUNT crawling ");
		     List<OutSideStockInfo> boardDataDomains =  getDataService.getBoardReplyInfo(companyDomains);
		     m_log.info("[Cro MAIN] GET COMPANY REPLY COUNT SUCCESS COUNT : " + boardDataDomains.size());
		     for(int i = 0; i<boardDataDomains.size();i++)
		     {
		    	 m_log.info("[Cro MAIN] SAVE REPLY_COUNT  CompanyName : " + boardDataDomains.get(i).getCompanyName()
		    			 + " , REPLY_COUNT : " + boardDataDomains.get(i).getTodayReplyCnt());

		    	 OutSideStockInfo outSideStockInfo = outSideStockInfoRepository.findByCompanyCodeAndSaveDateAfter( boardDataDomains.get(i).getCompanyCode()
		       			 ,CalendarUtil.getToday());
		    		
		    	 if(outSideStockInfo == null)
		    	 {
		    		 outSideStockInfoRepository.save(boardDataDomains.get(i));
		    	 }
		    	 else
		    	 {
		    		 outSideStockInfo.setTodayReplyCnt(boardDataDomains.get(i).getTodayReplyCnt());
		    		 outSideStockInfo.setSaveDate(boardDataDomains.get(i).getSaveDate());
		    		 outSideStockInfoRepository.save(outSideStockInfo);
		    	 }
		     }
			     
			// 4. data save to ReplyCount table
			// 이동 평균 ( 2주 평균 계산)
			/* 
			 select  avg(today_reply_cnt) as today_reply_cnt from fs_outside_stock_info where save_date between '2015-05-11 19:08:33.822' and '2015-05-26 19:08:33.822'
			*/
		     
			// 5. mail send	     
		    String title = "[Tron38X] Daily Notice Information";
		    String msg = this.mailContents();
		    if (msg != null) {
				stockMailSender.mailSend(title, msg, 0);
				m_log.info("[Cro MAIN] SEND MAIL TITLE : " + title + " , msg : " + msg );
		    } else {
		    	stockMailSender.mailSend(title, "동작확인 - 수신된 데이터가 없습니다!", 1);
		    }
			m_log.info("[Cro MAIN] CroMain Service END!");
			
		 }catch(Exception ex)
		 {
			 m_log.error("[Cro MAIN] CroMain Service ERROR : " + ex.toString());
		 }
		return 0;
	}
	
	/**
	 * Mail contents 생성
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String mailContents() throws Exception {
		
		engine.init();
		List<CompanyInfoModel> list = this.getCompnayNoticeInfo();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("list", list);
		String html = null;
		if (list.size() > 0 ) {
			html = VelocityEngineUtils.mergeTemplateIntoString(this.engine, "template.vm", "UTF-8", model);
		}

		return html;
	}
	
	@Override
	public Long getMovingAverageCount(List<BigDecimal> list, int listSize) {
		Long resultCount = 0L;
		SimpleMovingAverage sa = new SimpleMovingAverage();
		
		sa.MovingAverage(listSize);
		for (BigDecimal value : list) {
			sa.add(value);
		}
		resultCount = sa.getAverage().longValue();
		
		return resultCount;
	}
	
	/**
	 * 
	 * 메일 데이터 생성
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CompanyInfoModel> getCompnayNoticeInfo() throws Exception {
		
		List<CompanyInfoModel> cims = new ArrayList<CompanyInfoModel>();
		// 메일 보내는 시점이 오늘이므로 오늘 댓글 정보를 기준으로 Select 해야 함
		List<OutSideStockInfo> ossis = outSideStockInfoRepository.findBySaveDateAfter(CalendarUtil.getToday());
		
		if (ossis == null) {
			return null;
		}
		
		for(OutSideStockInfo ossi : ossis) {
			
			CompanyInfo ci = companyInfoRepository.findByCompanyCode(ossi.getCompanyCode());
			if (ci == null) {
				continue;
			}
			Long replyCnt = ossi.getTodayReplyCnt();
			Long fiveDaysAverageCnt = this.getFiveDaysAverageCount(ossi.getCompanyCode());
			Long twoWeeksAverageCnt = this.getTwoWeeksAverageCount(ossi.getCompanyCode());
			
			// 댓글수가 14일 이동평균값보다 많고, 5일 이동평균값이 14일 이동평균값보다 많을 경우
			if ( (replyCnt > twoWeeksAverageCnt) && (fiveDaysAverageCnt > twoWeeksAverageCnt) ) {
				CompanyInfoModel model = new CompanyInfoModel();
				model.setCompanyCode(ossi.getCompanyCode());
				model.setCompanyName(ossi.getCompanyName());
				model.setBoardUrl(ci.getBoardUrl());
				model.setMarketType(ci.getMarketType());
				model.setTodayReplyCnt(replyCnt);
				model.setFiveDaysAverageCnt(fiveDaysAverageCnt);
				model.setTwoWeeksAverageCnt(twoWeeksAverageCnt);
				cims.add(model);
			}
		}
	
		return cims;
	}
	
	public Long getFiveDaysAverageCount(String companyCode) throws Exception {
		Date fiveDaysAgo = CalendarUtil.getDayInterval(-4);
		List<OutSideStockInfo> lists = outSideStockInfoRepository.findByCompanyCodeAndSaveDateGreaterThan(companyCode,  fiveDaysAgo);
		Long resultCount = 0L;
		
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		for (OutSideStockInfo info : lists ) {
			list.add(new BigDecimal(info.getTodayReplyCnt().toString()));
		}
		resultCount = getMovingAverageCount(list, 5);
		return resultCount;
	}
	
	
	public Long getTwoWeeksAverageCount(String companyCode) throws Exception {
		Date twoWeeksAgo = CalendarUtil.getDayInterval(-13);
		List<OutSideStockInfo> lists = outSideStockInfoRepository.findByCompanyCodeAndSaveDateGreaterThan(companyCode, twoWeeksAgo);
		Long resultCount = 0L;
		
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		for (OutSideStockInfo info : lists ) {
			list.add(new BigDecimal(info.getTodayReplyCnt().toString()));
		}
		resultCount = getMovingAverageCount(list, 14);
		return resultCount;
	}
}
