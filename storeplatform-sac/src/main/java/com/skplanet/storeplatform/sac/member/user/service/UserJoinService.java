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
	 * 
	 */
	public CreateByMdnRes regByMdn(SacRequestHeader sacHeader, CreateByMdnReq req);

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
	 */
	public CreateByAgreementRes regByAgreementId(SacRequestHeader sacHeader, CreateByAgreementReq req);

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
	 */
	public CreateByAgreementRes regByAgreementDevice(SacRequestHeader sacHeader, CreateByAgreementReq req);

	/**
	 * <pre>
	 * ID 회원 간편 가입 (IDP 회원) [[ 단말정보 미포함 ]].
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateBySimpleReq
	 * @return CreateBySimpleRes
	 */
	public CreateBySimpleRes regBySimpleId(SacRequestHeader sacHeader, CreateBySimpleReq req);

	/**
	 * <pre>
	 * ID 회원 간편 가입 (IDP 회원) [[ 단말정보 포함 ]].
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateBySimpleReq
	 * @return CreateBySimpleRes
	 */
	public CreateBySimpleRes regBySimpleDevice(SacRequestHeader sacHeader, CreateBySimpleReq req);

	/**
	 * <pre>
	 * Save&Sync 가입.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateSaveAndSyncReq
	 * @return CreateSaveAndSyncRes
	 * 
	 */
	public CreateSaveAndSyncRes regSaveAndSync(SacRequestHeader sacHeader, CreateSaveAndSyncReq req);

	/**
	 * <pre>
	 * ID기반(Social ID) 회원 가입.
	 * </pre>
	 *
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateByIdSacReq
	 * @return CreateByIdSacRes
	 *
	 */
	public CreateByIdSacRes createById(SacRequestHeader sacHeader, CreateByIdSacReq req);

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입) V2.
	 * </pre>
	 *
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateByMdnV2SacReq
	 * @return CreateByMdnV2SacRes
	 *
	 */
	public CreateByMdnV2SacRes regByMdnV2(SacRequestHeader sacHeader, CreateByMdnV2SacReq req);
}
