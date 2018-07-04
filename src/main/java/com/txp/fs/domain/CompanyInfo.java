package com.txp.fs.domain;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fs_company_info")
public class CompanyInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 종목코드
	 */
	@Column(nullable = false,  unique = true) 
	private String companyCode;
	
	/**
	 * 회사명
	 */
	@Column(nullable = false)
	private String companyName;
	
	/**
	 * 회사 분류
	 */
	@Column
	private String companyType;
	
	/**
	 * 마켓 분류
	 */
	@Column
	private String marketType;
	
	/**
	 * 자본금
	 */
	@Column
	private String capital;
	
	/**
	 * 게시판 URL
	 */
	@Column
	private String boardUrl;
	
	/**
	 * 38 커뮤니케이션 등록일자
	 */
	@Column
	private String modifyDate;

	
    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;
    
    @OneToMany(mappedBy = "compnayInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OutSideStockInfo> outSideStockInfo;

    public CompanyInfo() {}
	
	public CompanyInfo(String companyCode, String companyName, String companyType, 
			String marketType, String boardUrl, String capital, String modifyDate) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.companyType = companyType;
		this.marketType = marketType;
		this.boardUrl = boardUrl;
		this.capital = capital;
		this.modifyDate = modifyDate;
	}

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

	@Override
	public String toString() {
		return "CompanyInfo [id=" + id + ", companyCode=" + companyCode
				+ ", companyName=" + companyName + ", companyType="
				+ companyType + ", marketType=" + marketType + ", capital="
				+ capital + ", boardUrl=" + boardUrl + ", modifyDate="
				+ modifyDate + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", outSideStockInfo=" + outSideStockInfo + "]";
	}
}
