/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
*
* ActvieProfileProvider
*
* Created on 2014. 12. 11. by 서대영, SK플래닛.
*/
@Component
public class ActvieProfileProvider {
	
	private static String activeProfile = "";
	
	@Value("#{systemProperties['spring.profiles.active']}") 
	public void setActiveProfile(String activeProfile) {
		ActvieProfileProvider.activeProfile = activeProfile;
	}
	
	public static String getActiveProfile() {
		return activeProfile;
	}

}
