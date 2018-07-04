package com.txp.fs.service;

import java.text.ParseException;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface OutSideStockInfoService {

	public Map<String, Object> getOutSideStockInfoList(Pageable pageable) throws ParseException, Exception;

	public int deleteBeforeOneMonthData() throws ParseException;
}
