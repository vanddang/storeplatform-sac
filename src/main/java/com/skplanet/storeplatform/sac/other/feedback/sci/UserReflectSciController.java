/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.sci;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.other.feedback.sci.UserReflectSci;
import com.skplanet.storeplatform.sac.client.internal.other.feedback.vo.UserReflectSacReq;
import com.skplanet.storeplatform.sac.other.feedback.service.UserReflectService;
import com.skplanet.storeplatform.sac.other.feedback.vo.UserToBeReflected;

/**
 * 회원 파트에서 변경되는 회원 정보를 전달받기 위한 내부 API
 *
 * Updated on : 2014. 02. 12. Updated by : 서대영
 */
@LocalSCI
public class UserReflectSciController implements UserReflectSci {

	@Autowired
	private UserReflectService service;

	/**
	 * 회원 정보를 전달 받아 기타 쪽 테이블을 업데이트 한다.
	 */
	@Override
	public void reflectUser(@Validated UserReflectSacReq req) {
		UserToBeReflected serverVo = this.bind(req);
		this.service.reflectUser(serverVo);
	}

	/**
	 * 회원 정보를 전달 받아 기타 쪽 테이블을 업데이트 한다.
	 */
	@Override
	public void reflectUsers(@Validated List<UserReflectSacReq> reqList) {
		List<UserToBeReflected> voList = this.bind(reqList);
		this.service.reflectUsers(voList);
	}

	private List<UserToBeReflected> bind(List<UserReflectSacReq> clientVoList) {
		List<UserToBeReflected> serverVoList = new ArrayList<UserToBeReflected>();
		for (UserReflectSacReq clientVo : clientVoList) {
			UserToBeReflected serverVo = this.bind(clientVo);
			serverVoList.add(serverVo);
		}
		return serverVoList;
	}

	private UserToBeReflected bind(UserReflectSacReq clientVo) {
		UserToBeReflected serverVo = new UserToBeReflected();
		serverVo.setTenantId(clientVo.getTenantId());
		serverVo.setOldUserId(clientVo.getOldUserId());
		serverVo.setNewUserId(clientVo.getNewUserId());
		serverVo.setOldUserKey(clientVo.getOldUserKey());
		serverVo.setNewUserKey(clientVo.getNewUserKey());
		return serverVo;
	}




}
