/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import java.util.Map;

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 사용자 내부 메서드 서비스
 * 
 * Updated on : 2014. 5. 20. Updated by : 심대진, 다모아 솔루션.
 */
public interface SearchUserSCIService {

	/**
	 * <pre>
	 * userKey 목록을 이용하여 회원정보 목록조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserReq
	 * @return
	 */
	public SearchUserSacRes srhUserByUserKey(SacRequestHeader sacHeader, SearchUserSacReq request);

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
	 * 회원 정보 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public DetailRes srhUser(DetailReq req, SacRequestHeader sacHeader);

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
	 * 휴대기기 목록 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListDeviceReq
	 * @return ListDeviceRes
	 */
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req);

	/**
	 * 휴대기기 단건 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param keyType
	 *            String
	 * @param keyString
	 *            String
	 * @param userKey
	 *            String
	 * @return DeviceInfo
	 */
	public DeviceInfo srhDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey);

	/**
	 * <pre>
	 * deviceKey 목록을 이용하여 회원정보 목록조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserDeviceSacReq
	 * @return Map<String, UserDeviceInfoSac>
	 */
	public Map<String, UserDeviceInfoSac> srhUserByDeviceKey(SacRequestHeader sacHeader, SearchUserDeviceSacReq request);

	/**
	 * <pre>
	 * 회원 OCB 정보 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public GetOcbInformationRes getOcbInformation(SacRequestHeader sacHeader, GetOcbInformationReq req);

	/**
	 * <pre>
	 * OCB 가입여부 IDP 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	/**
	 * <pre>
	 * OCB 가입여부 IDP 조회.
	 * </pre>
	 * 
	 * @param imSvcNo
	 *            String
	 * @return OCB 가입여부
	 */
	public boolean isOcbJoinIDP(String imSvcNo);

	/**
	 * <pre>
	 * deviceId, orderDt 이용하여 최근 회원정보(탈퇴포함) 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            SearchOrderUserByDeviceIdSacReq
	 * @return SearchOrderUserByDeviceIdSacRes
	 */
	public SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceId(SacRequestHeader header,
			SearchOrderUserByDeviceIdSacReq request);

	/**
	 * <pre>
	 * 회원 등급조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchUserGradeSacReq
	 * @return SearchUserGradeSacRes
	 */
	public SearchUserGradeSacRes searchUserGrade(SacRequestHeader header, SearchUserGradeSacReq req);

	/**
	 * <pre>
	 * 회원정보 조회(전시용).
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchUserForDisplaySacReq
	 * @return SearchUserForDisplaySacRes
	 */
	public SearchUserSegmentSacRes searchUserSegment(SacRequestHeader header, SearchUserSegmentSacReq req);
}
