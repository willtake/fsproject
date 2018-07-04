package com.txp.fs.domain.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.txp.fs.domain.CompanyInfo;

@Getter
@Setter
public class CompanyInfoModel extends CompanyInfo {

	private static final long serialVersionUID = 1L;
	
	private Long todayReplyCnt;
    private Date saveDate;
	private Long fiveDaysAverageCnt;
	private Long twoWeeksAverageCnt;
}
