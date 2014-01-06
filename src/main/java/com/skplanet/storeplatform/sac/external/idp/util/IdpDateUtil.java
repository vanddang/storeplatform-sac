/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 날짜 유틸리티(IDP)
 * 
 * Updated on : 2014. 1. 6. Updated by : Jeon.ByungYoul, SK planet.
 */
public class IdpDateUtil {

	public static SimpleDateFormat dateSqlFormat = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 현재 시간을 hhmmss 형식으로 반환한다.
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		DateFormat df = new SimpleDateFormat("hhmmss");
		return df.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * yyyymmdd형식의 날짜 문자열을 받아 Date형으로 반환한다.
	 * 
	 * @param yyyymmdd
	 *            년월일(예:20030329 -> 2003년 3월 29일)
	 * @return 날짜 표기에 적절치 않은 문자열 이라면 null을 반환한다.
	 */
	public static Date getDate(String yyyymmdd) {
		if (yyyymmdd != null && yyyymmdd.length() == 8 && isDigit(yyyymmdd)) {
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(yyyymmdd.substring(0, 4)), Integer.parseInt(yyyymmdd.substring(4, 6)) - 1,
					Integer.parseInt(yyyymmdd.substring(6, 8)));

			return cal.getTime();
		}

		return null;
	}

	/**
	 * yyyymm 형식의 날짜 문자열을 받아 Date형으로 반환한다.
	 * 
	 * @param yyyymm
	 *            년월일(예:200303 -> 2003년 3월)
	 * @return 날짜 표기에 적절치 않은 문자열 이라면 null을 반환한다.
	 */
	public static Date getDateYYYYMM(String yyyymm) {
		if (yyyymm != null && yyyymm.length() == 6 && isDigit(yyyymm)) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymm.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymm.substring(4, 6)) - 1);

			return cal.getTime();
		}

		return null;
	}

	/**
	 * 현재 날짜를 MM형식으로 반환한다.
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * 문자열이 숫자인지 체크
	 * 
	 * @param digitStr
	 *            숫자로 구성된 문자열
	 * @return 숫자로 구성되어 있으며 true, 아니면 false
	 */
	private static boolean isDigit(String digitStr) {
		if (digitStr != null) {
			for (int i = 0; i < digitStr.length(); i++)
				if (!Character.isDigit(digitStr.charAt(i)))
					return false;
		}
		return true;
	}

}
