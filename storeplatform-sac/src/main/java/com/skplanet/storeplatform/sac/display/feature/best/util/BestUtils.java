/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.best.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.skplanet.storeplatform.sac.common.util.DateUtils;

/**
 * 베스트 유틸리티 클래스
 * Created on 2014. 09. 16 by 서대영, SK 플래닛
 */
public class BestUtils {
	
	public static String getPastSunday(String stdDt) {
		Date date = DateUtils.parseDate(stdDt, "yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return DateFormatUtils.format(calendar, "yyyyMMdd");
	}
	
	/**
	 * 입력된 날짜가 입력된 리스트의 최신 수집 날짜와 동일한가?
	 * <pre>
	 * 최신 수집 날짜는 리스트의 집계 주기에 따라 달라진다.
	 * - 일간 : 당일
	 * - 주간 : 지나간 최근 일요일
	 * - 월간 : 그달 1일
	 * </pre>  
	 */
	public static boolean isListRecentlyCollectedOn(String listId, String stdDt) {
		Calendar calendar = null;
		
		if (isListCollectedDaily(listId)) {
			calendar = Calendar.getInstance();
		} else if (isListCollectedWeekly(listId)) {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 일요일
		} else if (isListCollectedMonthly(listId)) {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 1); // 1일
		} else {
			// 예외) 잘못된 listId 입니다.
		}
		
		String recentDate = DateFormatUtils.format(calendar, "yyyyMMdd");
		
		return stdDt.equals(recentDate);
	}

	/**
	 * 일간 집계 리스트인가?
	 */
	public static boolean isListCollectedDaily(String listId) {
		return "RNK000000006".equals(listId) || "RNK000000003".equals(listId);
	}
	
	/**
	 * 주간 집계 리스트인가?
	 */
	public static boolean isListCollectedWeekly(String listId) {
		return "RNK000000008".equals(listId) || "RNK000000005".equals(listId);
	}
	
	/**
	 * 월간 집계 리스트인가?
	 */
	public static boolean isListCollectedMonthly(String listId) {
		return "RNK000000007".equals(listId) || "RNK000000004".equals(listId);
	}
	
}
