package com.txp.fs.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.txp.fs.domain.OutSideStockInfo;

@Repository
public interface OutSideStockInfoRepository extends JpaRepository<OutSideStockInfo, Long> {

	public List<OutSideStockInfo> findAll();
	
	public OutSideStockInfo findById(Long id);
	
	public OutSideStockInfo findByCompanyCode(String companyCode);
	
	public OutSideStockInfo findByCompanyCodeAndSaveDateAfter(String companyCode, Date today);
	
	public Page<OutSideStockInfo> findBySaveDateAfterOrderByTodayReplyCntDesc(Date today, Pageable pageable);
	
	public List<OutSideStockInfo> findBySaveDateAfter(Date today);
	
	public List<OutSideStockInfo> findByCompanyCodeAndSaveDateGreaterThan(String companyCode, Date startDate);
	
	public List<OutSideStockInfo> findByCompanyCodeAndSaveDateBetween(String companyCode, Date startDate, Date endDate);
	
	@Modifying
	@Query("DELETE FROM OutSideStockInfo WHERE saveDate < :saveDate")
	public int deleteBySaveDateBefore(@Param("saveDate") Date saveDate);
}
