/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.other.feedback.vo.UserToBeReflected;

/**
 * 회원 파트에서 변경되는 회원 정보를 전달받기 위한 서비스 구현체
 *
 * Updated on : 2014. 02. 12. Updated by : 서대영
 */
@Service
@Transactional
public class UserReflectServiceFake implements UserReflectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserReflectServiceFake.class);

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.other.feedback.service.UserReflectService#moidfyUsersToBeUpdated()
	 */
	@Override
	public void reflectUsers(List<UserToBeReflected> users) {
		LOGGER.info("##### (Fake) Users are updated on DB successfully. #####");
	}

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.other.feedback.service.UserReflectService#reflectUser(com.skplanet.storeplatform.sac.other.feedback.vo.UserToBeReflected)
	 */
	@Override
	public void reflectUser(UserToBeReflected user) {
		LOGGER.info("##### (Fake) A user is updated on DB successfully. #####");
	}

}
