/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.sacservice.util;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceAuthType;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceSchedule;

/**
 * SacService 유틸리티
 *
 * Created on 2015. 01. 09. by 서대영, SK플래닛
 */
public class SacServiceUtils {
	
	public static boolean isAuthorized(SacServiceAuthType authType, List<String> list, String item) {
		if (authType == null) {
			throw new IllegalArgumentException("AuthType can't be null.");
		}
		
		switch (authType) {
		case WHITE:
			return list.contains(item);
		case BLACK:
			return !list.contains(item);
		case ALL:
		default:
			return true;
		}
	}
	
    public static boolean containsSchedule(List<SacServiceSchedule> list) {
		for (SacServiceSchedule schedule : list) {
			Date startDate = schedule.getStartDt();
			Date endDate = schedule.getEndDt();
			if (SacServiceUtils.isNowBetween(startDate, endDate)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNowBetween(Date startDate, Date endDate) {
    	DateTime startDateTime = new DateTime(startDate);
    	DateTime endDateTime = new DateTime(endDate);
    	return startDateTime.isBeforeNow() && endDateTime.isAfterNow();
    }

}
