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


	/**
	 * market pin 정보 이관
	 * - prevInsdUsermbrNo (모바일 회원) => newInsdUsermbrNo (아이디회원) 전환 시 이관
	 * - 모바일 회원 Market Pin 기준으로 변경
	 * @param prevInsdUsermbrNo
	 * @param newInsdUsermbrNo
	 * @return boolean 정상 이관 시 true / 이관 대상이 없거나 이관 실패 시 false
	 */
	boolean transferMarketPin(String prevInsdUsermbrNo, String newInsdUsermbrNo);
}
