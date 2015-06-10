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

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateSocialAccountSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.InitRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.InitRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveSocialAccountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 정보 수정 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
public interface UserModifyService {

	/**
	 * <pre>
	 * 회원 정보 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public ModifyRes mod(SacRequestHeader sacHeader, ModifyReq req);

	/**
	 * <pre>
	 * 비밀번호 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public ModifyPasswordRes modPassword(SacRequestHeader sacHeader, ModifyPasswordReq req);

	/**
	 * <pre>
	 * 이메일 주소 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public ModifyEmailRes modEmail(SacRequestHeader sacHeader, ModifyEmailReq req);

	/**
	 * <pre>
	 * Store 약관 동의 등록.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public CreateTermsAgreementRes regTermsAgreement(SacRequestHeader sacHeader, CreateTermsAgreementReq req);

	/**
	 * <pre>
	 * Store 약관 동의 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public ModifyTermsAgreementRes modTermsAgreement(SacRequestHeader sacHeader, ModifyTermsAgreementReq req);

	/**
	 * <pre>
	 * 실명 인증 정보 등록 (본인/법정대리인).
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public CreateRealNameRes regRealName(SacRequestHeader sacHeader, CreateRealNameReq req);

	/**
	 * <pre>
	 * 실명 인증 정보 초기화.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public InitRealNameRes initRealName(SacRequestHeader sacHeader, InitRealNameReq req);

	/**
	 * <pre>
	 * 2.1.56. 소셜 계정 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateSocialAccountSacReq
	 * @return CreateSocialAccountSacRes
	 */
	public CreateSocialAccountSacRes regSocialAccount(SacRequestHeader header, CreateSocialAccountSacReq req);

	/**
	 * <pre>
	 * 2.1.57. 소셜 계정 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveSocialAccountSacReq
	 * @return RemoveSocialAccountSacRes
	 */
	public RemoveSocialAccountSacRes removeSocialAccount(SacRequestHeader header, RemoveSocialAccountSacReq req);
}
