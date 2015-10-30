/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.member.client.common.constant.Constant;

// TODO: Auto-generated Javadoc

/**
 * 유틸리티 클래스.
 * 
 * Updated on : 2014. 1. 21 Updated by : wisestone_brian, wisestone
 */
public class Utils {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	/**
	 * Base64 to byte.
	 * 
	 * @param data
	 *            the data
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] base64ToByte(String data) throws IOException {
		return Base64.decodeBase64(data);
	}

	/**
	 * Byte to base64.
	 * 
	 * @param data
	 *            the data
	 * @return the string
	 */
	public static String byteToBase64(byte[] data) {
		return Base64.encodeBase64String(data);
	}

	/**
	 * 현재 날짜와 시각을 yyyyMMddhhmmssSSS 형태로 변환 후 return.
	 * 
	 * @return yyyyMMddhhmmssSSS.
	 * @see java.util.Date
	 * @see java.util.Locale
	 */
	public static String getLocalDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}

	/**
	 * Fill string with characters API.
	 * 
	 * @param str
	 *            base string (text).
	 * @param fixedLength
	 *            total string size (int).
	 * @param c
	 *            fill string (char).
	 * 
	 * @return string string (text).
	 */
	public static String leftPadStringWithChar(String str, int fixedLength, char c) {

		if (fixedLength < str.length()) {
			return str;
		}

		StringBuilder sb = new StringBuilder(str);

		for (int i = 0; i < fixedLength - str.length(); i++) {
			sb.insert(0, c);
		}

		return sb.toString();
	}

	/**
	 * 현재 날짜와 시각을 yyyyMMdd 형태로 변환 후 return.
	 * 
	 * @return yyyyMMdd.
	 * @see java.util.Date
	 * @see java.util.Locale
	 */
	public static String getLocalDateTimeinYYYYMMDD() {
		return getLocalDateTime("yyyyMMdd");
	}

	/**
	 * <pre>
	 * 현재 날짜와 시각을 요청 패턴 형태로 변환 후 return.
	 * </pre>
	 * 
	 * @param ptn
	 *            String
	 * @return String
	 */
	public static String getLocalDateTime(String ptn) {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = ptn;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}

	/**
	 * 객체의 필드를 프린트한다.
	 * 
	 * @param obj
	 *            the obj
	 * @return the string
	 */
	static public String printKeyValues(Object obj) {

		StringBuffer sb = new StringBuffer();
		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field field : fields) {

			try {
				field.setAccessible(true);
				String key = field.getName();
				String value;
				try {
					value = (String) field.get(obj);
				} catch (ClassCastException e) {
					value = "";
				}
				sb.append(key).append(": ").append(value).append("\n");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return sb.toString();
	}

	/**
	 * 데이터 수정시 사전에 정의한 String(Constant.DEFAULT_NODATA)을 치환하는 멤버 함수.
	 * 
	 * @param obj
	 *            치환하고자 하는 스트링을 포함하는 VO Object
	 * @return Object - 치환된 Object
	 */
	static public Object setDataMapping(Object obj) {

		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field field : fields) {

			try {
				field.setAccessible(true);
				String value;
				if (field.getType().getName().equalsIgnoreCase(Constant.DEFAULT_STRINGTYPE) != true)
					continue;
				try {
					value = (String) field.get(obj);
					if (value != null) {
						if (value.equalsIgnoreCase(Constant.DEFAULT_NODATA) == true)
							field.set(obj, "");
						else if (value.isEmpty() == true)
							field.set(obj, null);
					}
				} catch (ClassCastException e) {
					field.set(obj, null);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 랜덤한 패스워드 값을 생성하여 리턴한다.
	 * 
	 * @return password - 초기화된 패스워드
	 */
	static public String getInitPassword() {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABSDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random r = new Random(System.currentTimeMillis());
		char[] id = new char[8];

		for (int i = 0; i < 8; i++) {
			id[i] = chars[r.nextInt(chars.length)];
		}
		return new String(id);
	}

	/**
	 * <p>
	 * 문자열을 Date로 변환한다. 인식할 수 없는 패턴이거나 null이 주어지면 null을 리턴해준다.
	 * </p>
	 * 
	 * @param str
	 *            the date to parse. nullable
	 * @return the parsed date
	 */
	public static Date parseDate(String str) {
		return parseDate(str, "yyyyMMddHHmmss");
	}

	/**
	 * <pre>
	 * 문자열을 정해진 패턴의 Date 형식으로 변환한다.
	 * </pre>
	 * 
	 * @param str
	 *            요청 문자열
	 * @param ptn
	 *            String
	 * @return Date
	 */
	public static Date parseDate(String str, String ptn) {
		if (str == null)
			return null;

		SimpleDateFormat parser = new SimpleDateFormat();

		try {
			parser.applyPattern(ptn);
			return parser.parse(str);
		} catch (IllegalArgumentException ae) {
			return null;
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * <pre>
	 * List 분할.
	 * </pre>
	 * 
	 * @param capacityCount
	 *            분할 할 갯수
	 * @param targetList
	 *            targetList 분할 할 List
	 * @return HashMap 분할된 List 가 담길 Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<Integer, List> distributeListToSameCapacity(int capacityCount, List targetList) {
		HashMap<Integer, List> distributedList = new HashMap<Integer, List>();

		if (capacityCount <= 0) {
			distributedList.put(0, new ArrayList(targetList));
		} else {
			int toIndex = 0;
			int index = 0;

			for (int i = 0; i < targetList.size();) {
				toIndex = i + capacityCount;

				if (i > targetList.size()) {
					break;
				}

				if (toIndex > targetList.size())
					toIndex = targetList.size();

				distributedList.put(index, new ArrayList(targetList.subList(i, toIndex)));

				index++;
				i += capacityCount;
			}
		}

		return distributedList;
	}
}
