package com.txp.fs.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.txp.fs.domain.CompanyInfo;

@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {

	List<CompanyInfo> findAll();

	CompanyInfo findByCompanyCode(String companyCode);
	
	@Query(value = "select  company_code , avg(today_reply_cnt) as reply_avg from fs_outside_stock_info where save_date between :startDate and :endDate group by company_code", nativeQuery = true)
    List<CompanyInfo> findMoveAvg(Date startDate,Date endDate);

}
