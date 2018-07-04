package com.txp.fs.domain.model;

import lombok.Getter;
import lombok.Setter;

import com.txp.fs.domain.OutSideStockInfo;

@Getter
@Setter
public class OutSideStockInfoModel extends OutSideStockInfo {

	private static final long serialVersionUID = 1L;
	
	private String companyCode;
	private String companyName;
	private String CompanyType;
	private String marketType;
	private String capital;
	private String boardUrl;
	private String modifyDate;
	private Long averageCnt;
}
