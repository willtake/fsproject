package com.txp.fs.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SimpleMovingAverageTest {
	

	@Test
	public void getMovingAverageCountTest() {
		// Given
		SimpleMovingAverage sa = new SimpleMovingAverage();
		Long fiveAverage = 0L, twoWeeksAverage = 0L;
		int fiveDay = 5, twoWeeks = 14;
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		
		// When
		// 12, 38, 10, 9, 93
		list.add(new BigDecimal(12));
		list.add(new BigDecimal(38));
		list.add(new BigDecimal(10));
		list.add(new BigDecimal(9));
		list.add(new BigDecimal(93));
		
		list.add(new BigDecimal(12));
		list.add(new BigDecimal(38));
		list.add(new BigDecimal(10));
		list.add(new BigDecimal(9));
		list.add(new BigDecimal(93));
		
		list.add(new BigDecimal(12));
		list.add(new BigDecimal(38));
		list.add(new BigDecimal(10));
		list.add(new BigDecimal(9));
		list.add(new BigDecimal(93));
			
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(1));
		list.add(new BigDecimal(2));
		
		list.add(new BigDecimal(12));
		list.add(new BigDecimal(38));
		list.add(new BigDecimal(10));
		list.add(new BigDecimal(9));
		list.add(new BigDecimal(93));
		
		sa.MovingAverage(fiveDay);
//		sa.MovingAverage(twoWeeks);
		for (BigDecimal value : list) {
			sa.add(value);
		}
		
		// Then
		fiveAverage = sa.getAverage().longValue();
		System.out.println(fiveAverage);
		

//		twoWeeksAverage = sa.getAverage().longValue();
//		System.out.println(twoWeeksAverage);
	}

}
