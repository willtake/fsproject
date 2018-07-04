package com.txp.fs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fs_outside_stock_info")
public class OutSideStockInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 종목코드
	 */
	@Column
	private String companyCode;
	
	/**
	 * 회사명
	 */
	@Column(nullable = false)
	private String companyName;
	
	/**
	 * Daily Count
	 */
	@Column
	private Long todayReplyCnt;
	
	/**
	 * 저장일자
	 */
    @Column
    private Date saveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyInfo compnayInfo;
    
	public OutSideStockInfo() {}
	
	public OutSideStockInfo(String companyCode, String companyName, Long todayReplyCnt,
			Date saveDate) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.todayReplyCnt = todayReplyCnt;
		this.saveDate = saveDate;
	}

	@Override
	public String toString() {
		return "OutSideStockInfo [id=" + id + ", companyCode=" + companyCode
				+ ", companyName=" + companyName + ", todayReplyCnt="
				+ todayReplyCnt + ", saveDate=" + saveDate + ", compnayInfo="
				+ compnayInfo + "]";
	}   
}
