/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.CheckUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckUserMarketPinRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinRes;

/**
 * 회원 Market Pin
 * 
 * Updated on : 2015. 1. 18. Updated by : 임근대, SKP.
 */
public interface UserMarketPinService {

	/**
	 * <pre>
	 * 회원 Market Pin 등록 / 수정
	 * </pre>
	 *
	 * @param createMarketPinReq
	 * @return CreateMarketPinRes
	 */
	CreateUserMarketPinRes createMarketPin(CreateUserMarketPinReq createMarketPinReq);

	/**
	 * <pre>
	 * 회원 Market Pin 확인
	 * </pre>
	 *
	 * @param checkMarketPinReq
	 * @return CheckMarketPinRes
	 */
	CheckUserMarketPinRes checkMarketPin(CheckUserMarketPinReq checkMarketPinReq);

}
