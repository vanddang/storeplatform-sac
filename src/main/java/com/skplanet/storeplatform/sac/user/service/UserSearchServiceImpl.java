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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.user.vo.UserSearchMdnRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.UserSearchMdnResponseVO;

/**
 * Member Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 20. Updated by : 한서구, 부르칸.
 */
@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

	private final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);

	// @Autowired
	// private IDPSCI idpSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.user.service.UserSearchService#joinMdn(java .lang.String)
	 */
	@Override
	public UserSearchMdnResponseVO searchMdn(UserSearchMdnRequestVO reqVo) {
		UserSearchMdnResponseVO res = new UserSearchMdnResponseVO();

		System.out.println(reqVo.toString());
		res.setResultCode("10001");
		res.setResultMessage("연동 성공");
		res.setUserKey("1111111111");

		// this.logger.debug(this.idpSCI.alredySearchCheckByEmail("tlaeo00@naver.com").entrySet().toString());

		return res;
	}

}
