package com.txp.fs.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	public static Long diffOfDate(String begin, String end) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	 
	    Date beginDate = formatter.parse(begin);
	    Date endDate = formatter.parse(end);
	 
	    long diff = endDate.getTime() - beginDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	 
	    return diffDays;
	}
	
	public static Date getDayInterval(int add) throws ParseException {
		Calendar rightNow = null; // 현재 시간
		rightNow = Calendar.getInstance(); // 현재 시간
		rightNow.add(Calendar.DATE, add); 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String returnDate = sdf.format(rightNow.getTime());
	  
		return transFormat.parse(returnDate);
	}

	public static Date getToday() throws ParseException {
    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String returnDate = sdf.format(new Date());
	    
	    return transFormat.parse(returnDate);
	}
	
	public static Date getYesterday(Date today) throws ParseException {
	    if ( today == null ) 
	        throw new IllegalStateException ( "today is null" );
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	    Date yesterday = new Date();
	    yesterday.setTime(today.getTime() - ((long) 1000 * 60 * 60 * 24 ));
	    String returnDate = sdf.format(yesterday);
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    return transFormat.parse(returnDate);
	}
}
