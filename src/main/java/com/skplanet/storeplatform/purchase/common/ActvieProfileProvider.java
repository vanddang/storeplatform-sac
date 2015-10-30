/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * ActvieProfileProvider
 * 
 * Updated on : 2015. 3. 2. Updated by : 이승택, nTels.
 */
@Component
public class ActvieProfileProvider {

	private static String activeProfile = "";

	@Value("#{systemProperties['spring.profiles.active']}")
	public static void setActiveProfile(String activeProfile) {
		ActvieProfileProvider.activeProfile = activeProfile;
	}

	public static String getActiveProfile() {
		return activeProfile;
	}
}
