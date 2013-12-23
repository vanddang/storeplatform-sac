package com.skplanet.storeplatform.sac.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.user.vo.AuthRealNameSmsSendRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.AuthRealNameSmsSendResponseVO;

/**
 * Member Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김다슬, 인크로스.
 */
@Service
@Transactional
public class UserAuthRealNameServiceImpl implements UserAuthRealNameService {

	private final Logger logger = LoggerFactory.getLogger(UserAuthRealNameServiceImpl.class);

	@Override
	public AuthRealNameSmsSendResponseVO smsSend(AuthRealNameSmsSendRequestVO request) {
		AuthRealNameSmsSendResponseVO res = new AuthRealNameSmsSendResponseVO();

		System.out.println(request.toString());
		res.setResultCode("1001");
		res.setResultMessage("연동 성공");

		return res;
	}
}
