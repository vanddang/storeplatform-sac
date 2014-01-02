/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 일자, 시간과 관련된 다수의 기능
 * <p>
 * 일자패턴 사용시의 문법
 * 
 * <pre>
 *  Symbol   Meaning                 Presentation        Example
 *  ------   -------                 ------------        -------
 *  G        era designator          (Text)              AD
 *  y        year                    (Number)            1996
 *  M        month in year           (Text & Number)     July & 07
 *  d        day in month            (Number)            10
 *  h        hour in am/pm (1~12)    (Number)            12
 *  H        hour in day (0~23)      (Number)            0
 *  m        minute in hour          (Number)            30
 *  s        second in minute        (Number)            55
 *  S        millisecond             (Number)            978
 *  E        day in week             (Text)              Tuesday
 *  D        day in year             (Number)            189
 *  F        day of week in month    (Number)            2 (2nd Wed in July)
 *  w        week in year            (Number)            27
 *  W        week in month           (Number)            2
 *  a        am/pm marker            (Text)              PM
 *  k        hour in day (1~24)      (Number)            24
 *  K        hour in am/pm (0~11)    (Number)            0
 *  z        time zone               (Text)              Pacific Standard Time
 *  '        escape for text         (Delimiter)
 *  ''       single quote            (Literal)           '
 * 
 *  [예시]
 *  Format Pattern                         Result
 *  --------------                         -------
 *  "yyyyMMdd"                        ->>  19960710
 *  "yyyy-MM-dd"                      ->>  1996-07-10
 *  "HHmmss"                          ->>  210856
 *  "HH:mm:ss"                        ->>  21:08:56
 *  "hh:mm:ss"                        ->>  09:08:56
 *  "yyyy.MM.dd hh:mm:ss"             ->>  1996.07.10 15:08:56
 *  "EEE, MMM d, ''yy"                ->>  Wed, July 10, '96
 *  "h:mm a"                          ->>  12:08 PM
 *  "hh 'o''clock' a, zzzz"           ->>  12 o'clock PM, Pacific Daylight Time
 *  "K:mm a, z"                       ->>  0:00 PM, PST
 *  "yyyyy.MMMMM.dd GGG hh:mm aaa"    ->>  1996.July.10 AD 12:08 PM
 * 
 * </pre>
 * 
 * 기타 자세한 것은 "http://java.sun.com/j2se/1.3/docs/api/java/text/SimpleDateFormat.html" SimpleDateFormat Class API Document
 * 를 참조할것
 */

public class DateUtil {
	/**
	 * 문자열의 값이 일자값인지 검증
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return 일자값이면 true, 아니면 false
	 */
	public static boolean isDate(String textDate) {
		try {
			dateCheck(textDate);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 내부적인 Date Value Check용 임
	 * 
	 * @param textDate
	 */
	private static void dateCheck(String textDate) throws Exception {
		if (textDate.length() != 8)
			throw new Exception("[" + textDate + "] is not date value");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			sdf.setLenient(false);
			Date dt = sdf.parse(textDate);
		} catch (Exception e) {
			throw new Exception("[" + textDate + "] is not date value");
		}
		return;
	}

	/**
	 * 일자값을 가진 8자리 문자열로 Calendar 객체를 생성
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return Calendar 객체
	 */
	public static Calendar getCalendar(String textDate) throws Exception {
		// dateCheck(textDate);
		int year = Integer.parseInt(textDate.substring(0, 4));
		int month = Integer.parseInt(textDate.substring(4, 6));
		int date = Integer.parseInt(textDate.substring(6, 8));

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		if (textDate.length() == 14) {
			int hour = Integer.parseInt(textDate.substring(8, 10));
			int minute = Integer.parseInt(textDate.substring(10, 12));
			int second = Integer.parseInt(textDate.substring(12, 14));
			cal.set(year, month - 1, date, hour, minute, second);
		} else {
			cal.set(year, month - 1, date);
		}

		return cal;
	}

	/**
	 * 일자값을 가진 8자리 문자열로 Date 객체를 생성
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return Date 객체
	 */
	public static Date getDate(String textDate) throws Exception {
		return getCalendar(textDate).getTime();
	}

	/**
	 * 주어진 Date 객체를 이용하여 주어진 패턴 날짜형의 문자열을 구함.
	 * 
	 * @param date
	 *            원하는 일자의 Date 객체
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 주어진 Date 객체를 이용하여 기본날짜형('yyyyMMdd')의 문자열을 구함.
	 * 
	 * @param date
	 *            원하는 일자의 Date 객체
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(Date date) throws Exception {
		return getDateString(date, "yyyyMMdd");
	}

	/**
	 * 주어진 일자를 이용하여 주어진 패턴 날짜형의 문자열을 구함.
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(String textDate, String pattern) throws Exception {
		String date = null;
		if (textDate != null && !textDate.equals("")) {
			date = getDateString(getDate(textDate), pattern);
		} else {
			date = "";
		}
		return date;
	}

	/**
	 * 주어진 패턴 날짜형 시스템일자를 구함
	 * 
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 시스템 일자
	 */
	public static String getToday(String pattern) {
		return getDateString(new Date(), pattern);
	}

	/**
	 * 기본패턴('yyyyMMdd') 날짜형 시스템일자를 구함
	 * 
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 기본형('yyyyMMdd')의 시스템 일자
	 */
	public static String getToday() {
		return getToday("yyyyMMdd");
	}

	/**
	 * 기본패턴('HHmmss') 날짜형 시스템시간을 구함
	 * 
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 기본형('HHmmss')의 시스템 시간
	 */
	public static String getTime() {
		return getToday("HHmmss");
	}

	/**
	 * 지정한 분리자를 이용한 시스템일자를 구함
	 * 
	 * @param delmt
	 *            원하는 분리자 문자 예) ':', '/' ...
	 * @return 분리자가 삽입된 시스템 시간
	 */
	public static String getTime(char delmt) {
		return getToday("HH" + delmt + "mm" + delmt + "ss");
	}

	/**
	 * 지정된 일자로 부터 일정기간 이후의 일자를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param termDays
	 *            원하는 기간
	 * @param both
	 *            양편넣기 여부
	 * @return 일정기간 이후의 일자 ('yyyyMMdd')
	 */
	public static String getToDate(String fromDate, int termDays, boolean both) throws Exception {
		if (both)
			termDays = termDays - 1;
		Calendar cal = getCalendar(fromDate);
		cal.add(Calendar.DATE, termDays);
		return getDateString(cal.getTime(), "yyyyMMdd");
	}

	/**
	 * 지정된 시간으로부터 몇분 전후 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param termDays
	 *            원하는 기간 분
	 * @param both
	 *            양편넣기 여부
	 * @return 일정기간 이후의 일자 ('yyyyMMdd')
	 */
	public static String getToMinute(String fromDate, int termMins, boolean both) throws Exception {
		if (both)
			termMins = termMins - 1;
		Calendar cal = getCalendar(fromDate);
		cal.add(Calendar.MINUTE, termMins);
		return getDateString(cal.getTime(), "yyyyMMddHHmmss");
	}

	/**
	 * 지정된 개월수로 부터 일정기간의 달을 얻어온다.
	 * 
	 * @param fromDate
	 *            현재 일자
	 * @param termMonths
	 *            원하는 기간
	 * @param both
	 *            양편넣기 여부
	 * @return 일정기간 이후의 개월 ('yyyyMMdd')
	 * @throws Exception
	 */
	public static String getToMonth(String fromDate, int termMonths, boolean both) throws Exception {
		if (both)
			termMonths = termMonths - 1;
		Calendar cal = getCalendar(fromDate);
		cal.add(Calendar.MONTH, termMonths);
		return getDateString(cal.getTime(), "yyyyMMdd");
	}

	/**
	 * 해당되는 월의 마지막 일자를 얻어온다.
	 * 
	 * @param date
	 *            현재 일자
	 * @return 현재일자의 마지막 월 일자
	 * @throws Exception
	 */
	public static String getLastDayOfMonth(String date) throws Exception {
		Calendar cal = getCalendar(date);
		cal.roll(Calendar.MONTH, true);

		String firstDate = getDateString(cal.getTime(), "yyyyMM01");
		Calendar cal2 = getCalendar(firstDate);
		cal2.add(Calendar.DATE, -1);

		return getDateString(cal2.getTime(), "yyyyMMdd");

	}

	/**
	 * 지정된 일자로 부터 일정기간 이후의 일자를 한편넣기방식으로 구함.
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param termDays
	 *            원하는 기간
	 * @return 일정기간 이후의 일자 ('yyyyMMdd')
	 */
	public static String getToDate(String fromDate, int termDays) throws Exception {
		return getToDate(fromDate, termDays, false);
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @param both
	 *            양편넣기 여부
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(Date fromDate, Date toDate, boolean both) {
		long diffDays = toDate.getTime() - fromDate.getTime();
		long days = diffDays / (24 * 60 * 60 * 1000);
		if (both) {
			if (days >= 0)
				days += 1;
			else
				days -= 1;
		}
		return new Long(days).intValue();
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 한편넣기로 계산함.
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(Date fromDate, Date toDate) {
		return getDiffDays(fromDate, toDate, false);
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @param both
	 *            양편넣기 여부
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(String fromDate, String toDate, boolean both) throws Exception {
		return getDiffDays(getDate(fromDate), getDate(toDate), both);
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 한편넣기로 계산함.
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(String fromDate, String toDate) throws Exception {
		return getDiffDays(getDate(fromDate), getDate(toDate), false);
	}

	/**
	 * yyyyMMdd의 문자열을 yyyy-MM-dd로 변경
	 * 
	 * @param date
	 *            날짜 문자열
	 * @return
	 */
	public static String changeDateString(String date) {
		if (date.equals("")) {
			return "";
		}

		String yyyy = date.substring(0, 4);
		String MM = date.substring(4, 6);
		String dd = date.substring(6);

		return yyyy + "-" + MM + "-" + dd;
	}

	/**
	 * 어제 날짜 구하기
	 * 
	 * @param today
	 * @return
	 */
	public static Date getYesterday(Date today) {
		if (today == null)
			throw new IllegalStateException("today is null");
		Date yesterday = new Date();
		yesterday.setTime(today.getTime() - ((long) 1000 * 60 * 60 * 24));

		return yesterday;
	}

	public static void main(String[] args) {
		try {
			// System.out.println(getLastDayOfMonth("20040101"));
		} catch (Exception e) {

		}
	}

	/**
	 * yyyyMMdd의 문자열을 yyyy-MM-dd로 변경
	 * 
	 * @param date
	 *            날짜 문자열
	 * @return
	 */
	public static String changeDateStringForCouPonBiz(String date) {
		if (date.equals("")) {
			return "";
		}

		String yyyy = date.substring(0, 4);
		String MM = date.substring(4, 6);
		String dd = date.substring(6, 8);

		if (date.length() > 10) {
			String HH = date.substring(8, 10);
			String mm = date.substring(10, 12);

			return yyyy + "-" + MM + "-" + dd + " " + HH + ":" + mm;
		} else {
			return yyyy + "-" + MM + "-" + dd;
		}
	}

	/**
	 * yyyyMMddhhmmss 의 문자열을 yyyyMMdd로 변경
	 * 
	 * @param date
	 *            날짜 문자열
	 * @return jade 2013.10.23 추가
	 */
	public static String changeDateStringtoEight(String date) {
		if (date.equals("")) {
			return "";
		}
		if (date.length() > 8) {
			String yyyy = date.substring(0, 4);
			String MM = date.substring(4, 6);
			String dd = date.substring(6, 8);
			return yyyy + MM + dd;
		} else {
			return date;
		}
	}
}
