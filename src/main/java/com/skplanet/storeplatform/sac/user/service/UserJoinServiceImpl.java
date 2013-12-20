/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.idp.client.sci.IDPSCI;
import com.skplanet.storeplatform.sac.client.user.vo.UserJoinMdnRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.UserJoinMdnResponseVO;

/**
 * Member Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 심대진, 다모아 솔루션.
 */
@Service
@Transactional
public class UserJoinServiceImpl implements UserJoinService {

	private final Logger logger = LoggerFactory.getLogger(UserJoinServiceImpl.class);

	@Autowired
	private IDPSCI idpSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.user.service.UserJoinService#joinMdn(java.lang.String)
	 */
	@Override
	public UserJoinMdnResponseVO joinMdn(UserJoinMdnRequestVO reqVo) {
		UserJoinMdnResponseVO res = new UserJoinMdnResponseVO();

		System.out.println(reqVo.toString());
		res.setResultCode("1000");
		res.setResultMessage("연동 성공");
		res.setUserKey("1111111111");

		this.logger.debug(this.idpSCI.alredyJoinCheckByEmail("tlaeo00@naver.com").entrySet().toString());

		return res;
	}

	@Override
	public UserJoinMdnResponseVO joinIdPw(UserJoinMdnRequestVO reqVo) {
		UserJoinMdnResponseVO res = new UserJoinMdnResponseVO();
		this.logger.debug(reqVo.toString());
		return res;
	}

}
