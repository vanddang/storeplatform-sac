/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.example.internal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.internal.other.feedback.sci.UserReflectSci;
import com.skplanet.storeplatform.sac.client.internal.other.feedback.vo.UserReflectSacReq;

/**
 * 기타 파트 내부 API 호출 서비스
 *
 * Updated on : 2014. 2. 12.
 * Updated by : 서대영, SK 플래닛.
 */
@Service
public class OtherInvokeServiceImpl implements OtherInvokeService {

	@Autowired
	private UserReflectSci sci;

	@Override
	public void reflectOne(UserReflectSacReq req) {
		this.sci.reflectUser(req);
	}

	@Override
	public void reflectList(List<UserReflectSacReq> reqList) {
		this.sci.reflectUsers(reqList);
	}

}
