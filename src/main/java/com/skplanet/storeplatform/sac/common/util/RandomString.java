package com.skplanet.storeplatform.sac.common.util;

import java.util.Random;

/**
 * 난수 발생 유틸리티
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class RandomString {

	private static Random RND = new Random(System.currentTimeMillis());

	/**
	 * Number.
	 */
	public static final int TYPE_NUMBER = 1;
	/**
	 * Lower Alpha.
	 */
	public static final int TYPE_LOWER_ALPHA = 2;
	/**
	 * Upper Alpha.
	 */
	public static final int TYPE_UPPER_ALPHA = 4;
	/**
	 * All.
	 */
	public static final int TYPE_ALL = TYPE_NUMBER + TYPE_LOWER_ALPHA + TYPE_UPPER_ALPHA;

	/**
	 * Default ALL.
	 * 
	 * @param len
	 *            : randomString Length Setting
	 * @return String : randomString
	 */
	public static String getString(int len) {
		return getString(len, TYPE_ALL);
	}

	/**
	 * <pre>
	 * 난수 발생.
	 * </pre>
	 * 
	 * @param len
	 *            : randomString Length Setting
	 * @param type
	 *            : randomString Type Setting
	 * @return String : randomString
	 */
	public static String getString(int len, int type) {
		StringBuffer sb;

		sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int rndNum;
			char cp;

			switch (type) {
			case TYPE_NUMBER:
				rndNum = RND.nextInt(10);
				cp = (char) ('0' + rndNum);
				break;
			case TYPE_LOWER_ALPHA:
				rndNum = RND.nextInt(25);
				cp = (char) ('a' + rndNum);
				break;
			case TYPE_UPPER_ALPHA:
				rndNum = RND.nextInt(25);
				cp = (char) ('A' + rndNum);
				break;
			case TYPE_NUMBER + TYPE_LOWER_ALPHA:
				rndNum = RND.nextInt(35);
				if (rndNum < 10) {
					cp = (char) ('0' + rndNum);
				} else {
					cp = (char) ('a' + rndNum - 10);
				}
				break;
			case TYPE_NUMBER + TYPE_UPPER_ALPHA:
				rndNum = RND.nextInt(35);
				if (rndNum < 10) {
					cp = (char) ('0' + rndNum);
				} else {
					cp = (char) ('A' + rndNum - 10);
				}
				break;
			default:
				rndNum = RND.nextInt(60);
				if (rndNum < 10) {
					cp = (char) ('0' + rndNum);
				} else if (rndNum < 35) {
					cp = (char) ('a' + rndNum - 10);
				} else {
					cp = (char) ('A' + rndNum - 35);
				}
				break;
			}

			sb.append(cp);
		}

		return sb.toString();
	}

	/**
	 * <pre>
	 * Test Main.
	 * </pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println("              round : " + i);
			System.out.println("        Only number : " + RandomString.getString(4, RandomString.TYPE_NUMBER));
			System.out.println("    Only lower case : " + RandomString.getString(10, RandomString.TYPE_LOWER_ALPHA));
			System.out.println("    Only upper case : " + RandomString.getString(10, RandomString.TYPE_UPPER_ALPHA));
			System.out.println("lower case + Number : "
					+ RandomString.getString(10, RandomString.TYPE_NUMBER + RandomString.TYPE_LOWER_ALPHA));
			System.out.println("upper case + Number : "
					+ RandomString.getString(10, RandomString.TYPE_NUMBER + RandomString.TYPE_UPPER_ALPHA));
			System.out.println("                all : " + RandomString.getString(10));
		}
	}
}
