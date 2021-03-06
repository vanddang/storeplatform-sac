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

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckSocialAccountSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateSSOCredentialSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateSSOCredentialSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailForPayPlanetSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailForPayPlanetSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailV2Res;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistListSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistListSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDailyPhoneOsSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTenantReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTenantRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDeliveryInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDeliveryInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchGiftChargeInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchGiftChargeInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSac;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchSocialAccountSacRes;
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
	 * 회원 가입 여부 조회.
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
	 * 회원 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	// public DetailRes searchUserBase(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 회원 상세 정보 조회.
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
	 * 회원 정보 조회 SC API.
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
	 * 회원 징계정보 조회 SC API.
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	// public UserMbrPnsh searchUserMbrPnsh(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * 
	 * <pre>
	 * 디바이스 리스트 조회 SC API.
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
	 * 약관동의목록 조회 SC API.
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	// public SearchAgreementRes searchUserAgreement(DetailReq req, SacRequestHeader sacHeader);

	/**
	 * <pre>
	 * DeviceId를 이용하여 회원 정보 조회.
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
	 * OneID 정보조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 * @return MbrOneidSacRes
	 */
	public MbrOneidSacRes srhUserOneId(SacRequestHeader sacHeader, MbrOneidSacReq req);

	/**
	 * <pre>
	 * ID 찾기.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param deviceId
	 * @return SearchIdSacRes
	 */
	public SearchIdSacRes srhId(SacRequestHeader sacHeader, SearchIdSacReq req);

	/**
	 * <pre>
	 * PASSWORD 찾기.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param SearchPasswordSacReq
	 * @return SearchPasswordSacRes
	 */
	public SearchPasswordSacRes srhPassword(SacRequestHeader sacHeader, SearchPasswordSacReq req);

	/**
	 * <pre>
	 * 약관동의목록조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param SearchPasswordSacReq
	 * @return SearchPasswordSacRes
	 */
	public ListTermsAgreementSacRes listTermsAgreement(SacRequestHeader sacHeader, ListTermsAgreementSacReq req);

	/**
	 * <pre>
	 * 사용자 정보 조회 userEmail.
	 * </pre>
	 * 
	 * @param sacHeader
	 * @param SearchIdSacReq
	 * @return SearchIdSacRes
	 */
	public List<SearchIdSac> srhUserEmail(SearchIdSacReq req, SacRequestHeader sacHeader);

	/**
	 * <pre>
	 * 각 단말의 OS별 누적 가입자 수 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 * @return ListDailyPhoneOsSacRes
	 */
	public ListDailyPhoneOsSacRes listDailyPhoneOs(SacRequestHeader sacHeader);

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
	 * 사용자 정보 조회 V2.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            DetailReq
	 * @return DetailV2Res
	 */
	public DetailV2Res detailV2(SacRequestHeader sacHeader, DetailReq req);

	/**
	 * <pre>
	 * 2.1.50. 회원 가입 여부 리스트 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ExistListSacReq
	 * @return ExistListSacRes
	 */
	public ExistListSacRes existList(SacRequestHeader sacHeader, ExistListSacReq req);

	/**
	 * 2.1.56. 가입 테넌트 정보 목록 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListTenantReq
	 * @return ListTenantRes
	 */
	public ListTenantRes listTenant(SacRequestHeader requestHeader, ListTenantReq req);

	/**
	 * <pre>
	 * 2.1.58. 소셜 계정 등록 가능 여부 체크.
	 * </pre>
	 * 
	 * @param header
	 *            CheckSocialAccountSacReq
	 * @param req
	 *            CheckSocialAccountSacReq
	 * @return CheckSocialAccountSacRes
	 */
	public CheckSocialAccountSacRes checkSocialAccount(SacRequestHeader header, CheckSocialAccountSacReq req);

	/**
	 * <pre>
	 * 2.1.59. 소셜 계정 등록 회원 리스트.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchSocialAccountSacReq
	 * @return SearchSocialAccountSacRes
	 */
	public SearchSocialAccountSacRes searchSocialAccount(SacRequestHeader header, SearchSocialAccountSacReq req);

	/**
	 * <pre>
	 * 2.1.61.	PayPlanet SSOCredential 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SacRequestHeader
	 * @return CreateSSOCredentialSacRes
	 */
	public CreateSSOCredentialSacRes createSSOCredential(SacRequestHeader header, CreateSSOCredentialSacReq req);

	/**
	 * <pre>
	 * 2.1.64.	배송지 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchDeliveryInfoSacReq
	 * @return SearchDeliveryInfoSacRes
	 */
	public SearchDeliveryInfoSacRes searchDeliveryInfo(SacRequestHeader header, SearchDeliveryInfoSacReq req);

	/**
	 * <pre>
	 * 2.1.65.	회원 상품권 충전 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchGiftChargeInfoSacReq
	 * @return SearchGiftChargeInfoSacRes
	 */
	public SearchGiftChargeInfoSacRes searchGiftChargeInfo(SacRequestHeader header, SearchGiftChargeInfoSacReq req);

	/**
	 * <pre>
	 * 2.1.78.	PayPlanet 회원 정보 조회.
	 * </pre>
	 *
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailForPayPlanetSacReq
	 * @return DetailForPayPlanetSacRes
	 */
	public DetailForPayPlanetSacRes detailForPayPlanet(SacRequestHeader header, DetailForPayPlanetSacReq req);
}
