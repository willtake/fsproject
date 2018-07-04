package com.txp.fs.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.txp.fs.common.util.CalendarUtil;
import com.txp.fs.component.SimpleMovingAverage;
import com.txp.fs.domain.CompanyInfo;
import com.txp.fs.domain.OutSideStockInfo;
import com.txp.fs.domain.model.CompanyInfoModel;
import com.txp.fs.repository.CompanyInfoRepository;
import com.txp.fs.repository.OutSideStockInfoRepository;


@Service
public class OutSideStockInfoServiceImpl implements OutSideStockInfoService {
	
	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	private OutSideStockInfoRepository outSideStockInfoRepository;

	@Override
	public Map<String, Object> getOutSideStockInfoList(Pageable pageable) throws Exception {
		
		Page<OutSideStockInfo> ossis = outSideStockInfoRepository.findBySaveDateAfterOrderByTodayReplyCntDesc(CalendarUtil.getYesterday(new Date()), pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		
        int current = ossis.getNumber() + 1;
        int totalPage = ossis.getTotalPages();
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, totalPage); 
        List<CompanyInfoModel> cim = this.convertComapanyInfoModel(ossis);
       
        map.put("cms", cim);
        map.put("beginIndex", begin);
        map.put("endIndex", end);
        map.put("currentIndex", current);
        map.put("totalPage", totalPage);
		
		return map;
	}
	
	@Transactional
	@Override
	public int deleteBeforeOneMonthData() throws ParseException {
		Date oneMonthDay = CalendarUtil.getDayInterval(-30);
		return outSideStockInfoRepository.deleteBySaveDateBefore(oneMonthDay);
	}
	
	private List<CompanyInfoModel> convertComapanyInfoModel(Page<OutSideStockInfo> ossis) throws Exception {
		
		List<CompanyInfoModel> cms = new ArrayList<CompanyInfoModel>();

		for(OutSideStockInfo ossi : ossis) {
			CompanyInfoModel model = new CompanyInfoModel();
			
			CompanyInfo ci = companyInfoRepository.findByCompanyCode(ossi.getCompanyCode());
			if (ci == null) {
				continue;
			}
			
			Long replyCnt = ossi.getTodayReplyCnt();
			Long fiveDaysAverageCnt = this.getFiveDaysAverageCount(ossi.getCompanyCode());
			Long twoWeeksAverageCnt = this.getTwoWeeksAverageCount(ossi.getCompanyCode());
			
			model.setId(ossi.getId());
			model.setCompanyCode(ossi.getCompanyCode());
			model.setCompanyName(ossi.getCompanyName());
			model.setCapital(ci.getCapital());
			model.setMarketType(ci.getCompanyType());
			model.setTodayReplyCnt(replyCnt);
			model.setFiveDaysAverageCnt(fiveDaysAverageCnt);
			model.setTwoWeeksAverageCnt(twoWeeksAverageCnt);
			model.setBoardUrl(ci.getBoardUrl());
			model.setModifyDate(ci.getModifyDate());
			model.setSaveDate(ossi.getSaveDate());
			
			cms.add(model);
		}

		return cms;
	}
	
	private Long getFiveDaysAverageCount(String companyCode) throws Exception {
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
	
	private Long getTwoWeeksAverageCount(String companyCode) throws Exception {
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
}
