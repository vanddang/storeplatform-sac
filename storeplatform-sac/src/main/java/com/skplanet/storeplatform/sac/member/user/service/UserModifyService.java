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

import com.skplanet.storeplatform.sac.client.member.vo.user.*;
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
	public ModifyRes modUser(SacRequestHeader sacHeader, ModifyReq req);

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

	/**
	 * <pre>
	 * 2.1.62.	배송지 정보 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateDeliveryInfoSacReq
	 * @return CreateDeliveryInfoSacRes
	 */
	public CreateDeliveryInfoSacRes createDeliveryInfo(SacRequestHeader header, CreateDeliveryInfoSacReq req);

	/**
	 * <pre>
	 * 2.1.63.	배송지 정보 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeliveryInfoSacReq
	 * @return RemoveDeliveryInfoSacRes
	 */
	public RemoveDeliveryInfoSacRes removeDeliveryInfo(SacRequestHeader header, RemoveDeliveryInfoSacReq req);

	/**
	 * <pre>
	 * 2.1.68. ID 변경 [신규 규격].
	 * </pre>
	 *
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            ModifyIdSacReq
	 * @return ModifyIdSacRes
	 */
	public ModifyIdSacRes modifyId(SacRequestHeader header, ModifyIdSacReq req);

}
