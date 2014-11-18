/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.util;

/**
 * <p>
 * StatMemberUtils
 * </p>
 * Updated on : 2014. 11. 05 Updated by : 서대영, SK 플래닛.
 */
public class StatMemberUtils {

	public static int getResCount(int dbListSize, int reqCount) {
		if (dbListSize == 0) {
			return 0;
		}
		
		if (!hasResNext(dbListSize, reqCount)) {
			return dbListSize;
		}
		
		return dbListSize - 1; // for hasNext
	}

	public static Integer getResStartKey(int dbListSize, int reqCount, int reqStartKey) {
		if (!hasResNext(dbListSize, reqCount)) {
			return null;
		}
		
		return reqStartKey + reqCount;
	}
	
	public static boolean hasResNext(int dbListSize, int reqCount) {
		return dbListSize > reqCount;
	}

}
