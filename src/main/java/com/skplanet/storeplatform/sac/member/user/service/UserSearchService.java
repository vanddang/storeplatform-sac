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

import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserMbrPnsh;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
public interface UserSearchService {

	/**
	 * <pre>
	 * 회원 가입 여부 조회
	 * </pre>
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ExistRes exist(SacRequestHeader sacHeader, ExistReq req);

	/**
	 * 
	 * <pre>
	 * 회원 기본 정보 조회
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public DetailRes searchUserBase(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 회원 상세 정보 조회
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public DetailRes detail(SacRequestHeader sacHeader, DetailReq req);

	/**
	 * 
	 * <pre>
	 * 회원 프로비저닝 이력 조회
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public GetProvisioningHistoryRes getProvisioningHistory(SacRequestHeader sacHeader, GetProvisioningHistoryReq req);

	/**
	 * 
	 * <pre>
	 * 회원 정보 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public UserInfo searchUser(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 회원 부가정보 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes listUserExtra(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 회원 징계정보 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public UserMbrPnsh searchUserMbrPnsh(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 디바이스 리스트 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ListDeviceRes listDevice(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 약관동의목록 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public SearchAgreementRes searchUserAgreement(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * <pre>
	 * 실명 인증 정보 등록.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public DetailByDeviceIdSacRes detailByDeviceId(SacRequestHeader sacHeader, DetailByDeviceIdSacReq req);

	/**
	 * <pre>
	 * OneID 정보조회
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 * @return MbrOneidSacRes
	 */
	public MbrOneidSacRes searchUserOneId(SacRequestHeader sacHeader, MbrOneidSacReq req);

	/**
	 * <pre>
	 * ID 찾기
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param deviceId
	 * @return SearchIdSacRes
	 */
	public SearchIdSacRes searchId(SacRequestHeader sacHeader, SearchIdSacReq req);
}
