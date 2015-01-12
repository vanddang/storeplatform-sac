package com.skplanet.storeplatform.sac.display.feature.best.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

public class BestUtilsTest {
	
	@Test
	public void testGetPastSunday() {
		assertEquals("20140907", BestUtils.getPastSunday("20140913"));
		assertEquals("20140907", BestUtils.getPastSunday("20140907"));
		assertEquals("20140831", BestUtils.getPastSunday("20140901"));
	}

	@Test
	public void testIsRecentlyCollectedOnDaily() {
		Calendar calendar = Calendar.getInstance();
		String today = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		calendar.add(Calendar.DATE, 1);
		String tomorrow = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		calendar.add(Calendar.DATE, -2);
		String yesterday = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		System.out.printf("today = %s\n", today);
		System.out.printf("tomorrow = %s\n", tomorrow);
		System.out.printf("yesterday = %s\n", yesterday);
		
		// BEST 다운로드 – 유료 – 일간
		assertTrue(BestUtils.isListRecentlyCollectedOn("RNK000000006", today));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000006", tomorrow));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000006", yesterday));
		
		// BEST 다운로드 – 무료 – 일간
		assertTrue(BestUtils.isListRecentlyCollectedOn("RNK000000003", today));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000003", tomorrow));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000003", yesterday));
	}
	
	@Test
	public void testIsRecentlyCollectedOnWeekly() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String sunday = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		String nextWeek = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		calendar.add(Calendar.WEEK_OF_MONTH, -2);
		String previousWeek = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		System.out.printf("sunday = %s\n", sunday);
		System.out.printf("nextWeek = %s\n", nextWeek);
		System.out.printf("previousWeek = %s\n", previousWeek);
		
		// BEST 다운로드 – 유료 – 주간
		assertTrue(BestUtils.isListRecentlyCollectedOn("RNK000000008", sunday));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000008", nextWeek));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000008", previousWeek));
		
		// BEST 다운로드 – 무료 – 주간
		assertTrue(BestUtils.isListRecentlyCollectedOn("RNK000000005", sunday));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000005", nextWeek));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000005", previousWeek));
	}
	
	@Test
	public void testIsRecentlyCollectedOnMonthly() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		String firstDay = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		calendar.add(Calendar.MONTH, 1);
		String nextMonth = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		calendar.add(Calendar.MONTH, -2);
		String previousMonth = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		System.out.printf("firstDay = %s\n", firstDay);
		System.out.printf("nextWeek = %s\n", nextMonth);
		System.out.printf("previousWeek = %s\n", previousMonth);
		
		// BEST 다운로드 – 유료 – 월간
		assertTrue(BestUtils.isListRecentlyCollectedOn("RNK000000007", firstDay));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000007", nextMonth));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000007", previousMonth));
		
		// BEST 다운로드 – 무료 – 월간
		assertTrue(BestUtils.isListRecentlyCollectedOn("RNK000000004", firstDay));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000004", nextMonth));
		assertFalse(BestUtils.isListRecentlyCollectedOn("RNK000000004", previousMonth));
	}

}
