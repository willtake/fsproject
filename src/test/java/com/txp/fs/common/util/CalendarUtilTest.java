package com.txp.fs.common.util;

import java.text.ParseException;
import java.util.Date;
import org.junit.Test;

public class CalendarUtilTest {
	
	@Test
	public void testDiffOfDate() throws Exception {	
		Long result = CalendarUtil.diffOfDate("20150601","20150630");
		System.out.println(result);
	}

	@Test
	public void testGetDayInterval() throws ParseException {
		Date date = CalendarUtil.getDayInterval(10);
		System.out.println(date);
	}
	
	@Test
	public void testGetToday() throws ParseException {
		Date date = CalendarUtil.getToday();
		System.out.println(date);
	}

	@Test
	public void testGetYesterday() throws ParseException {
		Date date = CalendarUtil.getYesterday(new Date());
		System.out.println(date);
	}
}
