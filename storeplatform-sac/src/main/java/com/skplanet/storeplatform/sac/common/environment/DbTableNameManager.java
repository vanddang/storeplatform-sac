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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
*
* DbTableNameManager
*
* Created on 2014. 12. 11. by 서대영, SK플래닛.
*/
@Component
public class DbTableNameManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbTableNameManager.class);
	
	private static final String STG_POSTFIX = "_STG$";
	
	@Autowired
	private Environment environment;
	
	/**
	 * 상용 스테이징 환경일 경우, 테이블 이름에 접미사를 붙여준다.
	 */
	public String fixDbTableName(String name) {
		String[] activeProfiles = environment.getActiveProfiles();
		if (activeProfiles == null || activeProfiles.length == 0) {
			return name;
		}
		
		if ("stg".equals(activeProfiles[0])) {
			String fixedName = name + STG_POSTFIX;
			LOGGER.debug("The DB table name is fixed to {}.", fixedName);
			return fixedName;
		}
		return name;
	}

}
