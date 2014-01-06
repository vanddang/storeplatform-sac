package com.skplanet.storeplatform.sac.member.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;

/**
 * 회원 로그인 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private DeviceService deviceService;

	@Override
	public AuthorizeByMdnRes authorizeByMdn(AuthorizeByMdnReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorizeByIdRes authorizeById(AuthorizeByIdReq req) {
		// TODO Auto-generated method stub
		return null;
	}

}
