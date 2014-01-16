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

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
public interface UserJoinService {

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입).
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateByMdnReq
	 * @return CreateByMdnRes
	 * @throws Exception
	 *             Exception
	 * 
	 */
	public CreateByMdnRes createByMdn(SacRequestHeader sacHeader, CreateByMdnReq req) throws Exception;

	/**
	 * <pre>
	 * ID 회원 약관 동의 가입 (One ID 회원) [[ 단말정보 미포함 ]].
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateByAgreementReq
	 * @return CreateByAgreementRes
	 * @throws Exception
	 *             Exception
	 */
	public CreateByAgreementRes createByAgreementId(SacRequestHeader sacHeader, CreateByAgreementReq req) throws Exception;

	/**
	 * <pre>
	 * ID 회원 약관 동의 가입 (One ID 회원) [[ 단말정보 포함 ]].
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateByAgreementReq
	 * @return CreateByAgreementRes
	 * @throws Exception
	 *             Exception
	 */
	public CreateByAgreementRes createByAgreementDevice(SacRequestHeader sacHeader, CreateByAgreementReq req) throws Exception;

}
