package com.skplanet.storeplatform.sac.member.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 4. 17. Updated by : Rejoice, Burkhan
 */
public class DateUtils {

	/**
	 * <pre>
	 *    현재 날짜를 yyyyMMdd 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param None
	 * @return String yyyyMMdd 형식의 현재날짜
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString("yyyyMMdd");
	}

	/**
	 * <pre>
	 *    현재 시각을  HHmmss 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param None
	 * @return String HHmmss 형식의 현재 시각
	 */
	public static String getCurrentTimeString() {
		return getCurrentDateString("HHmmss");
	}

	/**
	 * <pre>
	 *    현재날짜를 주어진 pattern 에 따라 반환한다.
	 * </pre>
	 * 
	 * @param pattern
	 *            SimpleDateFormat 에 적용할 pattern
	 * @return String pattern 형식의 현재날짜
	 */
	public static String getCurrentDateString(String pattern) {
		return convertToString(getCurrentTimeStamp(), pattern);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param dateData
	 *            yyyyMMdd 형식의 날짜
	 * @return String yyyy/MM/dd 형식의 해당 날짜
	 */
	public static String convertFormat(String dateData) {
		return convertFormat(dateData, "yyyy/MM/dd");
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param dateData
	 *            yyyyMMdd 형식의 날짜
	 * @param format
	 *            SimpleDateFormat 에 적용할 pattern
	 * @return String pattern 형식의 해당 날짜
	 */
	public static String convertFormat(String dateData, String format) {

		return convertToString(convertToTimestamp(dateData), format);

	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param None
	 * @return Timestamp 현재 Timestamp 값
	 */
	public static Timestamp getCurrentTimeStamp() {
		try {
			Calendar cal = new GregorianCalendar();
			Timestamp result = new Timestamp(cal.getTime().getTime());
			return result;
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}

	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param None
	 * @return Timestamp 현재 Timestamp 값
	 */
	public static String getCurrentTime(String timeZone, String formant) {
		try {
			TimeZone tz;
			Date date = new Date();
			DateFormat df = new SimpleDateFormat(formant);
			tz = TimeZone.getTimeZone(timeZone);
			df.setTimeZone(tz);
			return df.format(date);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param timeZone
	 * @return String
	 */
	public static String getCurrentTime(String timeZone) {
		return getCurrentTime(timeZone, "yyyyMMddHHmmss");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param dateData
	 * @param toFormat
	 * @return String
	 */
	public static String convertFormatU(String dateData, String toFormat) {
		String yearString = dateData.substring(6, 10);
		String monthString = dateData.substring(0, 2);
		String dayString = dateData.substring(3, 5);
		String time = "000000";
		dateData = yearString + monthString + dayString + time;
		return convertToString(convertToTimestamp(dateData), toFormat);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 Timestamp 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param dateData
	 *            Timestamp 형식의 날짜
	 * @return String yyyy/MM/dd 형식의 Timestamp 날짜
	 */
	public static String convertToString(Timestamp dateData) {

		return convertToString(dateData, "yyyy/MM/dd");

	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 Timestamp 날짜를 pattern 에 따른 형식으로 반환한다.
	 * </pre>
	 * 
	 * @param dateData
	 *            Timestamp 형식의 날짜
	 * @param pattern
	 *            SimpleDateFormat 에 적용할 pattern
	 * @return String yyyy/MM/dd 형식의 Timestamp 날짜
	 */
	public static String convertToString(Timestamp dateData, String pattern) {
		return convertToString(dateData, pattern, java.util.Locale.KOREA);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 Timestamp 날짜를 pattern 과 locale  에 따른 형식으로 반환한다.
	 * 
	 * </pre>
	 * 
	 * @param dateData
	 *            Timestamp 형식의 날짜
	 * @param pattern
	 *            SimpleDateFormat 에 적용할 pattern
	 * @param locale
	 *            국가별 LOCALE
	 * @return String pattern 형식의 Timestamp 날짜
	 */
	public static String convertToString(Timestamp dateData, String pattern, java.util.Locale locale) {
		try {

			if (dateData == null) {
				return null;
			}

			SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
			// formatter.applyPattern( pattern );

			return formatter.format(dateData);
		} catch (Exception e) {
			throw new StorePlatformException("[DateUtil][convertToString]" + e.getMessage(), e);
		}

	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의  날짜를 Timestamp 로  반환한다.
	 * </pre>
	 * 
	 * @param dateData
	 *            yyyyMMdd 형식의 날짜
	 * @return Timestamp 형식의 해당 날짜
	 */
	public static Timestamp convertToTimestamp(String dateData) {

		try {

			if (dateData == null)
				return null;
			if (dateData.trim().equals(""))
				return null;

			int dateObjLength = dateData.length();

			String yearString = "2002";
			String monthString = "01";
			String dayString = "01";

			if (dateObjLength >= 4) {
				yearString = dateData.substring(0, 4);
			}
			if (dateObjLength >= 6) {
				monthString = dateData.substring(4, 6);
			}
			if (dateObjLength >= 8) {
				dayString = dateData.substring(6, 8);
			}

			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString) - 1;
			int day = Integer.parseInt(dayString);

			Calendar cal = new GregorianCalendar();
			cal.set(year, month, day);
			// cal.getTime();
			return new Timestamp(cal.getTime().getTime());

		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}

	}

	/**
	 * <pre>
	 *    yyyyMMddHHmmss 형식의  날짜시각을 Timestamp 로  반환한다.
	 * </pre>
	 * 
	 * @param dateData
	 *            yyyyMMddHHmmss 형식의 날짜시각
	 * @return Timestamp 형식의 해당 날짜시각
	 */
	public static Timestamp convertToTimestampHMS(String dateData) {
		try {

			if (dateData == null)
				return null;
			if (dateData.trim().equals(""))
				return null;

			String yearString = dateData.substring(0, 4);
			String monthString = dateData.substring(4, 6);
			String dayString = dateData.substring(6, 8);
			String hourString = dateData.substring(8, 10);
			String minString = dateData.substring(10, 12);
			String secString = dateData.substring(12, 14);

			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString) - 1;
			int day = Integer.parseInt(dayString);
			int hour = Integer.parseInt(hourString);
			int min = Integer.parseInt(minString);
			int sec = Integer.parseInt(secString);

			Calendar cal = new GregorianCalendar();
			cal.set(year, month, day, hour, min, sec);

			return new Timestamp(cal.getTime().getTime());

		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}

	}

	/**
	 * <pre>
	 * check date string validation with an user defined format.
	 * </pre>
	 * 
	 * @param s
	 *            date string you want to check.
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return date java.util.Date
	 */
	private static java.util.Date check(String s, String format) {
		if (s == null)
			throw new StorePlatformException("SAC_MEM_0096");
		if (format == null)
			throw new StorePlatformException("SAC_MEM_0096");

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		java.util.Date date = null;
		try {
			date = formatter.parse(s);
		} catch (java.text.ParseException e) {
			/*
			 * throw new java.text.ParseException( e.getMessage() + " with format \"" + format + "\"",
			 * e.getErrorOffset() );
			 */
			throw new StorePlatformException("SAC_MEM_0097");
		}

		if (!formatter.format(date).equals(s)) // StorePlatformException
			throw new StorePlatformException("SAC_MEM_0097");
		return date;
	}

	/**
	 * <pre>
	 * check date string validation with the default format "yyyyMMdd".
	 * </pre>
	 * 
	 * @param s
	 *            date string you want to check with default format "yyyyMMdd"
	 * @return boolean true 날짜 형식이 맞고, 존재하는 날짜일 때 false 날짜 형식이 맞지 않거나, 존재하지 않는 날짜일 때
	 */
	public static boolean isValid(String s) {
		return DateUtils.isValid(s, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * check date string validation with an user defined format.
	 * </pre>
	 * 
	 * @param s
	 *            date string you want to check.
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return boolean true 날짜 형식이 맞고, 존재하는 날짜일 때 false 날짜 형식이 맞지 않거나, 존재하지 않는 날짜일 때
	 */
	public static boolean isValid(String s, String format) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = null;
			try {
				date = formatter.parse(s);
			} catch (java.text.ParseException e) {
				return false;
			}

			if (!formatter.format(date).equals(s))
				return false;

			return true;

		} catch (Exception e) {
			throw new StorePlatformException("[DateUtil][isValid]" + e.getMessage(), e);
		}
	}

	/**
	 * <pre>
	 * return days between two date strings with default defined
	 * format.(yyyyMMdd)
	 * </pre>
	 * 
	 * @param s
	 *            date string you want to check.
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 요일을 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생 0: 일요일
	 *         (java.util.Calendar.SUNDAY 와 비교) 1: 월요일 (java.util.Calendar.MONDAY 와 비교) 2: 화요일
	 *         (java.util.Calendar.TUESDAY 와 비교) 3: 수요일 (java.util.Calendar.WENDESDAY 와 비교) 4: 목요일
	 *         (java.util.Calendar.THURSDAY 와 비교) 5: 금요일 (java.util.Calendar.FRIDAY 와 비교) 6: 토요일
	 *         (java.util.Calendar.SATURDAY 와 비교) 예) String s = "20000529"; int dayOfWeek = whichDay(s, format); if
	 *         (dayOfWeek == java.util.Calendar.MONDAY) logger.debug(" 월요일: " + dayOfWeek); if (dayOfWeek ==
	 *         java.util.Calendar.TUESDAY) logger.debug(" 화요일: " + dayOfWeek);
	 */
	public static int whichDay(String s) {
		return whichDay(s, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return days between two date strings with user defined format.
	 * </pre>
	 * 
	 * @param s
	 *            date string you want to check.
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 요일을 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생 0: 일요일
	 *         (java.util.Calendar.SUNDAY 와 비교) 1: 월요일 (java.util.Calendar.MONDAY 와 비교) 2: 화요일
	 *         (java.util.Calendar.TUESDAY 와 비교) 3: 수요일 (java.util.Calendar.WENDESDAY 와 비교) 4: 목요일
	 *         (java.util.Calendar.THURSDAY 와 비교) 5: 금요일 (java.util.Calendar.FRIDAY 와 비교) 6: 토요일
	 *         (java.util.Calendar.SATURDAY 와 비교) 예) String s = "2000-05-29"; int dayOfWeek = whichDay(s, "yyyy-MM-dd");
	 *         if (dayOfWeek == java.util.Calendar.MONDAY) logger.debug(" 월요일: " + dayOfWeek); if (dayOfWeek ==
	 *         java.util.Calendar.TUESDAY) logger.debug(" 화요일: " + dayOfWeek);
	 */
	public static int whichDay(String s, String format) {
		return whichDay(s, format, java.util.Locale.KOREA);
	}

	/**
	 * <pre>
	 * whichDay
	 * </pre>
	 * 
	 * @param s
	 * @param format
	 * @param locale
	 */
	public static int whichDay(String s, String format, Locale locale) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, locale);
			java.util.Date date = check(s, format);

			java.util.Calendar calendar = formatter.getCalendar();
			calendar.setTime(date);
			return calendar.get(java.util.Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			throw new StorePlatformException("[DateUtil][whichDay]" + e.getMessage(), e);
		}
	}

	/**
	 * <pre>
	 * return days between two date strings with default defined
	 * format.("yyyyMMdd")
	 * </pre>
	 * 
	 * @param String
	 *            from date string
	 * @param String
	 *            to date string
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static int daysBetween(String from, String to) {
		return daysBetween(from, to, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return days between two date strings with user defined format.
	 * </pre>
	 * 
	 * @param String
	 *            from date string
	 * @param String
	 *            to date string
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일자 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static int daysBetween(String from, String to, String format) {

		java.util.Date d1 = check(from, format);
		java.util.Date d2 = check(to, format);

		long duration = d2.getTime() - d1.getTime();

		return (int) (duration / (1000 * 60 * 60 * 24));
	}

	/**
	 * <pre>
	 * return years between two date strings with default defined
	 * format.("yyyyMMdd")
	 * </pre>
	 * 
	 * @param String
	 *            from date string
	 * @param String
	 *            to date string
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static int yearsBetween(String from, String to) {
		return yearsBetween(from, to, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return years between two date strings with user defined format.
	 * </pre>
	 * 
	 * @param String
	 *            from date string
	 * @param String
	 *            to date string
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static int yearsBetween(String from, String to, String format) {
		return daysBetween(from, to, format) / 365;
	}

	/**
	 * <pre>
	 * addMinute
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 분수
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 분수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addMinute(String s, int minute) {
		return addMinute(s, minute, "yyyyMMddHHmm");
	}

	/**
	 * <pre>
	 * addMinute
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param String
	 *            더할 분수
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 분수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addMinute(String s, int minute, String format) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(s, format);

			date.setTime(date.getTime() + ((long) minute * 1000 * 60));
			return formatter.format(date);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * addSecond
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 초수
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 초수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addSecond(String s, int second) {
		return addSecond(s, second, "yyyyMMddHHmmss");
	}

	/**
	 * <pre>
	 * addSecond
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param String
	 *            더할 초수
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 초수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addSecond(String s, int second, String format) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(s, format);

			date.setTime(date.getTime() + ((long) second * 1000));
			return formatter.format(date);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * addMilliSecond
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 millisecond
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 초수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addMilliSecond(String s, int milliSecond) {
		return addMilliSecond(s, milliSecond, "yyyyMMddHHmmss");
	}

	/**
	 * <pre>
	 * addMilliSecond
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param String
	 *            더할 millisecond
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 초수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addMilliSecond(String s, int milliSecond, String format) {
		try {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(s, format);

			date.setTime(date.getTime() + (milliSecond));
			return formatter.format(date);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * return add day to date strings
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 일수
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addDays(String s, int day) {
		return addDays(s, day, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return add day to date strings with user defined format.
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param String
	 *            더할 일수
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addDays(String s, int day, String format) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(s, format);

			date.setTime(date.getTime() + ((long) day * 1000 * 60 * 60 * 24));
			return formatter.format(date);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * return add month to date strings
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 월수
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 월수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addMonths(String s, int month) {
		return addMonths(s, month, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return add month to date strings with user defined format.
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 월수
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 월수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addMonths(String s, int addMonth, String format) {
		try {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(s, format);

			java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
			java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
			java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);
			int year = Integer.parseInt(yearFormat.format(date));
			int month = Integer.parseInt(monthFormat.format(date));
			int day = Integer.parseInt(dayFormat.format(date));

			month += addMonth;
			if (addMonth > 0) {
				while (month > 12) {
					month -= 12;
					year += 1;
				}
			} else {
				while (month <= 0) {
					month += 12;
					year -= 1;
				}
			}
			java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
			java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
			String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month))
					+ String.valueOf(twoDf.format(day));
			java.util.Date targetDate = null;

			targetDate = check(tempDate, "yyyyMMdd");
			// day = lastDay(year, month);
			// tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month))
			// + String.valueOf(twoDf.format(day));
			// targetDate = check(tempDate, "yyyyMMdd");

			return formatter.format(targetDate);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * return add year to date strings
	 * </pre>
	 * 
	 * @param String
	 *            s string
	 * @param int 더할 년수
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 년수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */

	public static String addYears(String s, int year) {
		return addYears(s, year, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return add year to date strings with user defined format.
	 * </pre>
	 * 
	 * @param String
	 *            date string
	 * @param int 더할 년수
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 년수 더하기 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String addYears(String s, int year, String format) {
		try {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(s, format);
			date.setTime(date.getTime() + ((long) year * 1000 * 60 * 60 * 24 * (365)));
			return formatter.format(date);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	// 입력된 시간으로부터 추가된 시간 계산 (day, hour, minute)
	// getAddedTime("200706290225", "minut", 2);
	// getAddedTime("2007062902", "hour", 1);
	public static String getAddedTime(String currentTime, String tTime, int nAdd) {
		java.util.Date current = new java.util.Date();
		java.text.SimpleDateFormat formatter = null;
		try {
			// 1일 추가 => 1일 : 24시간, 1시간 : 60분, 1분 : 60초 => ((long) nAdd * 1000 *
			// 60 * 60 * 24)
			// 1분 추가 => ((long) nAdd * 1000 * 60)
			if (tTime.equals("day")) {
				formatter = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
				current = formatter.parse(currentTime);
				current.setTime(current.getTime() + ((long) nAdd * 1000 * 60 * 60 * 24));
			} else if (tTime.equals("hour")) {
				formatter = new java.text.SimpleDateFormat("yyyyMMddHH", java.util.Locale.KOREA);
				current = formatter.parse(currentTime);
				current.setTime(current.getTime() + ((long) nAdd * 1000 * 60 * 60));
			} else if (tTime.equals("minute")) {
				formatter = new java.text.SimpleDateFormat("yyyyMMddHHmm", java.util.Locale.KOREA);
				current = formatter.parse(currentTime);
				current.setTime(current.getTime() + ((long) nAdd * 1000 * 60));
			}
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
		return formatter.format(current);
	}

	/**
	 * <pre>
	 * return months between two date strings
	 * </pre>
	 * 
	 * @param String
	 *            from date string
	 * @param String
	 *            to date string
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 개월수 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static int monthsBetween(String from, String to) {
		return monthsBetween(from, to, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * return months between two date strings with user defined format.
	 * </pre>
	 * 
	 * @param String
	 *            from date string
	 * @param String
	 *            to date string
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의개월수 리턴 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static int monthsBetween(String from, String to, String format) {
		try {

			java.util.Date fromDate = check(from, format);
			java.util.Date toDate = check(to, format);

			if (fromDate.compareTo(toDate) == 0)
				return 0;

			java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
			java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
			java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

			int fromYear = Integer.parseInt(yearFormat.format(fromDate));
			int toYear = Integer.parseInt(yearFormat.format(toDate));
			int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
			int toMonth = Integer.parseInt(monthFormat.format(toDate));
			int fromDay = Integer.parseInt(dayFormat.format(fromDate));
			int toDay = Integer.parseInt(dayFormat.format(toDate));

			int result = 0;
			result += ((toYear - fromYear) * 12);
			result += (toMonth - fromMonth);

			if (((toDay - fromDay) > 0))
				result += toDate.compareTo(fromDate);

			return result;
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * 그달의 마지말 날을 구함
	 * </pre>
	 * 
	 * @param String
	 *            src string
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 그달의 마지말 날을 구함 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String lastDayOfMonth(String src) {
		return lastDayOfMonth(src, "yyyyMMdd");
	}

	/**
	 * <pre>
	 * 그달의 마지말 날을 구함
	 * </pre>
	 * 
	 * @param format
	 *            string representation of the date format. For example, "yyyy-MM-dd".
	 * @return String 날짜 형식이 맞고, 존재하는 날짜일 때 그달의 마지말 날을 구함 형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 */
	public static String lastDayOfMonth(String src, String format) {
		try {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
			java.util.Date date = check(src, format);

			java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
			java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);

			int year = Integer.parseInt(yearFormat.format(date));
			int month = Integer.parseInt(monthFormat.format(date));
			int day = lastDay(year, month);

			java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
			java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
			String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month))
					+ String.valueOf(twoDf.format(day));

			java.util.Date targetDate = check(tempDate, "yyyyMMdd");

			return formatter.format(targetDate);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
	}

	/**
	 * <pre>
	 * int lastDay
	 * </pre>
	 * 
	 * @param year
	 * @param month
	 */
	private static int lastDay(int year, int month) {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			if ((year % 4) == 0) {
				if ((year % 100) == 0 && (year % 400) != 0) {
					day = 28;
				} else {
					day = 29;
				}
			} else {
				day = 28;
			}
			break;
		default:
			day = 30;
		}
		return day;
	}

	/**
	 * <pre>
	 * 첫 번째 인자와 두 번째 인자를 비교하여 첫번째 인자가 두번째 인자 보다 이전 날짜인지 비교하는 메소드
	 * </pre>
	 * 
	 * ex) f = 20010203 s=20010205 -> true 리턴
	 * 
	 * @param f
	 *            yyyyMMdd 형식의 날짜
	 * @param s
	 *            yyyyMMdd 형식의 날짜
	 * @return boolean
	 */
	public static boolean isPreviousDate(Timestamp f, Timestamp s) {
		return s.getTime() - f.getTime() >= 0;
	}

	/**
	 * <pre>
	 * 첫 번째 인자와 두 번째 인자를 비교하여 첫번째 인자가 두번째 인자 보다 이전 날짜인지 비교하는 메소드
	 * </pre>
	 * 
	 * ex) f = 20010203 s=20010205 -> true 리턴
	 * 
	 * @param f
	 *            yyyyMMdd 형식의 날짜
	 * @param s
	 *            yyyyMMdd 형식의 날짜
	 * @return boolean
	 */
	public static boolean isPreviousDate(String f, String s) throws java.text.ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date firstDate = formatter.parse(f);
		Date secondDate = formatter.parse(s);

		return secondDate.getTime() - firstDate.getTime() >= 0;
	}

	/**
	 * <pre>
	 * 특정 format 날짜로 원하는 format의 날짜를 리턴.
	 * </pre>
	 * 
	 * @param dateStr
	 *            - formated 날짜
	 * @param dateFormat
	 *            - format
	 * @param returnFormat
	 *            - 리턴받고자하는 format
	 * @return String
	 */
	public static String getDateFormatedString(String dateStr, String dateFormat, String returnFormat) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat.toLowerCase());
		SimpleDateFormat returnFormatter = new SimpleDateFormat(returnFormat.toLowerCase());
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			throw new StorePlatformException("SAC_MEM_0097");
		}
		return returnFormatter.format(date);
	}

	/**
	 * <pre>
	 * 두 시간의 갭을 반환
	 * </pre>
	 * 
	 * @param f
	 *            yyyyMMdd 형식의 날짜
	 * @param s
	 *            yyyyMMdd 형식의 날짜
	 * @return boolean
	 */
	public static float getTimeGap(String strStartTime, String strEndTime) {
		String sPad = "0000000000000000000";
		if (strStartTime.length() > strEndTime.length()) {
			strEndTime = strEndTime + sPad.substring(0, strStartTime.length() - strEndTime.length());
		}
		if (strStartTime.length() < strEndTime.length()) {
			strStartTime = strStartTime + sPad.substring(0, strEndTime.length() - strStartTime.length());
		}
		long startTime = Long.parseLong(strStartTime);
		long endTime = Long.parseLong(strEndTime);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(endTime);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(startTime);
		float frslt = (endTime - startTime) / 1000.0f;
		return frslt;
	}

	/**
	 * <pre>
	 *  CRONTAB 시간 표현으로 변경 (day, hour, minute).
	 * </pre>
	 * 
	 * @param timeType
	 * @param toTime
	 * @return String
	 */
	public static String getCrontabTime(String timeType, String toTime) {
		String convertTime = "";

		if (timeType.equals("minute")) {
			convertTime = "*/" + toTime + " *" + " *" + " *";
		} else if (timeType.equals("hour")) {
			convertTime = "*" + " " + "*/" + toTime + " *" + " *";
		} else if (timeType.equals("day")) {
			convertTime = "*" + " *" + " " + "*/" + toTime + " *";
		}
		convertTime += " *";
		return convertTime;
	}

	/**
	 * <pre>
	 * 지정된 format을 SimpleDateFormat의 문법에 입각하여 formatting한다.<BR>
	 * </pre>
	 * 
	 * @param String
	 *            format SimpleDateFormat의 문법에 맞는 format 문자열
	 * @return 주어진 format이 적용된 date string
	 */
	public static String getDateFormat(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, java.util.Locale.KOREA);
		return dateFormat.format(new java.util.Date());
	}

	/**
	 * <pre>
	 * 주민등록번호 13자리값을 받아 현재의 만 나이를 계산한다.
	 * 
	 * 일반 만나이 계산법 (회원 가입시 14세 미만 체크시 해당)
	 * --------------------------------------------------- (생일이 지난 경우) 만 나이: 현재
	 * 연도 - 태어난 연도(주민등록상) (생일이 안 지난 경우) 만 나이: 현재 연도 - 태어난 연도(주민등록상) - 1
	 * 
	 * 청소년보호법 관련 19세 계산법 ---------------------------------------------------- 위
	 * 만나이 계산법에서 생일이 지난경우로 계산합니다. 연 나이: 현재 연도 - 태어난 연도(주민등록상)
	 * </pre>
	 * 
	 * @param ssn
	 *            주민등록번호(13자리)
	 * @param ageCalculus
	 *            계산법 구분 (1: 일반 만나이, 2:청소년보호법)
	 * @return 현재의 만 나이
	 */
	public static String getAge(String ssn, String ageCalculus) {

		int _age = 0;

		// 주민번호13자리
		if (ssn != null && ssn.length() == 13) {

			// 주민등록번호 자르기 (년/월일/성별구분)
			int ssnYear = Integer.parseInt(ssn.substring(0, 2)); // 년
			int ssnDate = Integer.parseInt(ssn.substring(2, 6)); // 월일
			int sexGubun = Integer.parseInt(ssn.substring(6, 7)); // 남자/여자

			/*
			 * 주민번호2 의 성별 구분값으로 년도 재계산 2000년 이전에 태어난 사람은 남자 1, 여자 2로 시작 2000년 이후에 태어난 사람은 남자 3, 여자 4로 시작
			 */
			if (sexGubun == 1 || sexGubun == 2 || sexGubun == 5 || sexGubun == 6) {
				ssnYear = 1900 + ssnYear;
			} else if (sexGubun == 3 || sexGubun == 4 || sexGubun == 7 || sexGubun == 8) {
				ssnYear = 2000 + ssnYear;
			} else if (sexGubun == 9 || sexGubun == 0) {
				ssnYear = 1800 + ssnYear;
			}
			// 오늘날짜분석
			int currYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())); // 년
			int currDate = Integer.parseInt(new SimpleDateFormat("MMdd").format(new Date())); // 월일

			// 일반 만나이 계산(생일이 지났는지 확인)
			if (ageCalculus.equals("1"))
				_age = currYear - ssnYear - ((currDate - ssnDate < 0) ? 1 : 0);
			else
				_age = currYear - ssnYear;
		}

		return String.valueOf(_age);
	}

	/**
	 * <pre>
	 * 주민등록번호 13자리값을 받아 현재의 만 나이를 계산한다.
	 * </pre>
	 * 
	 * @param ssn
	 *            주민등록번호(13자리)
	 * @return 현재의 만 나이
	 */
	public static String getAge(String ssn) {
		// default : 일반 만나이 계산
		return getAge(ssn, "1");
	}

	/**
	 * <pre>
	 * yyyymmdd 8자리값을 받아 현재의 만 나이를 계산한다.
	 * </pre>
	 * 
	 * @param birthDay
	 *            yyyymmdd 생년월일
	 * @return 현재의 만 나이
	 */
	public static int getOnlyAge(String birthDay) {

		int _age = 0;
		if (isValid(birthDay)) {
			int ssnYear = Integer.parseInt(birthDay.substring(0, 4)); // 년
			int ssnDate = Integer.parseInt(birthDay.substring(4, 8)); // 월일
			// 오늘날짜분석
			int currYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())); // 년
			int currDate = Integer.parseInt(new SimpleDateFormat("MMdd").format(new Date())); // 월일

			_age = currYear - ssnYear - ((currDate - ssnDate < 0) ? 1 : 0);
		}

		return _age;
	}

	/**
	 * 어떤 날짜가( 1998.01.02, 98.01.02, 19980102, 980102 ) 들어오건 간에 YYYYMMDD로 변환한다.
	 * 
	 * @param argDate
	 *            - 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
	 * @return String - 변환된 일자
	 */
	public static String toYYYYMMDDDate(String argDate) {
		boolean isMunja = false;
		boolean isCorrectArg = true;
		String subArg = "";
		String date = "";
		String result = "";

		if (argDate != null)
			subArg = argDate.trim();

		for (int inx = 0; inx < subArg.length(); inx++) {
			if (java.lang.Character.isLetter(subArg.charAt(inx)) || subArg.charAt(inx) == ' ') {
				isCorrectArg = false;
				break;
			}
		}

		if (!isCorrectArg) {
			throw new StorePlatformException("SAC_MEM_0097");
		}

		if (subArg.length() != 8) {
			if (subArg.length() != 6 && subArg.length() != 10) {
				throw new StorePlatformException("SAC_MEM_0097");
			}

			if (subArg.length() == 6) {
				if (Integer.parseInt(subArg.substring(0, 2)) > 50)
					date = "19";
				else
					date = "20";

				result = date + subArg;
			}

			if (subArg.length() == 10)
				result = subArg.substring(0, 4) + subArg.substring(5, 7) + subArg.substring(8, 10);
		} else {// 8자린 경우 ( 98.01.02, 19980102 )

			try {
				Integer.parseInt(subArg);
			} catch (NumberFormatException ne) {
				isMunja = true;
			}

			if (isMunja) // 98.01.02 혹은 98/01/02 형식의 포맷일 경우
			{
				date = subArg.substring(0, 2) + subArg.substring(3, 5) + subArg.substring(6, 8);
				if (Integer.parseInt(subArg.substring(0, 2)) > 50)
					result = "19" + date;
				else
					result = "20" + date;
			} else
				// 19980102 형식의 포맷인 경우
				return subArg;
		}
		return result;
	}

	/**
	 * 입력된 일자를 Date 객체로 반환한다.
	 * 
	 * @param year
	 *            - 년
	 * @param month
	 *            - 월
	 * @param date
	 *            - 일
	 * @return Date - 해당일자에 해당하는 Date
	 */
	public static Date createDate(int year, int month, int date) {
		return createCalendar(year, month, date).getTime();
	}

	/**
	 * 입력된 일자를 Calendar 객체로 반환한다.
	 * 
	 * @param year
	 *            - 년
	 * @param month
	 *            - 월
	 * @param date
	 *            - 일
	 * @return Calendar - 해당일자에 해당하는 Calendar
	 */
	public static Calendar createCalendar(int year, int month, int date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(year, month - 1, date);
		return calendar;
	}

	/**
	 * 입력된 일자를 Date 객체로 반환한다.
	 * 
	 * @param argDate
	 *            - 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
	 * @return Calendar - 해당일자에 해당하는 Calendar
	 */
	public static Date toDate(String pDate) {
		String date = toYYYYMMDDDate(pDate);

		return createDate(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)),
				Integer.parseInt(date.substring(6, 8)));
	}
}
