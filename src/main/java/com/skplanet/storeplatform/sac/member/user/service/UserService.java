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

import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 사용자 공통 서비스.
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트.
 */
public interface UserService {
	/**
	 * 사용자 정보 IDP 연동(휴대기기 정보 포함).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            사용자 Key
	 * @param userAuthKey
	 *            사용자 IDP 인증키
	 */
	public void modProfileIdp(SacRequestHeader requestHeader, String userKey, String userAuthKey);

	/**
	 * <pre>
	 * userAuthKey가 없는경우 통합아이디에 붙은 MDN 휴대기기 정보 수정.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 * @param imSvcNo
	 *            String
	 * @return UpdateAdditionalInfoEcRes
	 */
	public void modAdditionalInfoForNonLogin(SacRequestHeader requestHeader, String userKey, String imSvcNo);

	/**
	 * <pre>
	 * 정상인 회원정보를 휴면 DB로 이동한다.
	 * </pre>
	 * 
	 * @param sacHeader
	 * @param moveUserInfoSacReq
	 * @return MoveUserInfoSacRes
	 */
	public MoveUserInfoSacRes moveUserInfo(SacRequestHeader sacHeader, MoveUserInfoSacReq moveUserInfoSacReq);
}
