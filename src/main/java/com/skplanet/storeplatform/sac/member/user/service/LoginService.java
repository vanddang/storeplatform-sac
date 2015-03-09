package com.skplanet.storeplatform.sac.member.user.service;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForOllehMarketSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForOllehMarketSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForUplusStoreSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForUplusStoreSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV2SacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV2SacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV3SacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV3SacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 로그인 관련 인터페이스.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
public interface LoginService {

	/**
	 * 모바일 전용 회원 인증 (MDN 인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	public AuthorizeByMdnRes authorizeByMdn(SacRequestHeader requestHeader, AuthorizeByMdnReq req);

	/**
	 * 모바일 전용 회원 인증 v2 (MDN 인증, 변동성 포함).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	public AuthorizeByMdnRes authorizeByMdnV2(SacRequestHeader requestHeader, AuthorizeByMdnReq req);

	/**
	 * <pre>
	 * 변동성 회원 체크.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CheckVariabilityReq
	 * @return CheckVariabilityRes
	 */
	public CheckVariabilityRes checkVariability(SacRequestHeader requestHeader, CheckVariabilityReq req);

	/**
	 * ID 기반 회원 인증 (One ID, IDP 회원).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByIdReq
	 * @return AuthorizeByIdRes
	 */
	public AuthorizeByIdRes authorizeById(SacRequestHeader requestHeader, AuthorizeByIdReq req);

	/**
	 * 심플 인증(간편인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForAutoUpdateReq
	 * @return AuthorizeByIdRes
	 */
	public AuthorizeSimpleByMdnRes authorizeSimpleByMdn(SacRequestHeader requestHeader, AuthorizeSimpleByMdnReq req);

	/**
	 * <pre>
	 * Save&Sync.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSaveAndSyncByMacReq
	 * @return AuthorizeSaveAndSyncByMacRes
	 */
	public AuthorizeSaveAndSyncByMacRes authorizeSaveAndSyncByMac(SacRequestHeader requestHeader,
			AuthorizeSaveAndSyncByMacReq req);

	/**
	 * <pre>
	 * PayPlanet에 InApp용으로 제공되는 T Store 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	public AuthorizeForInAppSacRes authorizeForInApp(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req);

	/**
	 * <pre>
	 * PayPlanet에 InApp용으로 제공되는 3사 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	public AuthorizeForInAppSacRes authorizeForInAppV2(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req);

	/**
	 * <pre>
	 * PayPlanet에 제공되는 T Store 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSacReq
	 * @return AuthorizeSacRes
	 */
	public AuthorizeSacRes authorize(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeSacReq req);

	/**
	 * <pre>
	 * Olleh Market 회원의 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForOllehMarketSacReq
	 * @return AuthorizeForOllehMarketSacRes
	 */
	public AuthorizeForOllehMarketSacRes authorizeForOllehMarket(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForOllehMarketSacReq req);

	/**
	 * <pre>
	 * Olleh Market 회원의 회원인증 성능테스트용.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForOllehMarketSacReq
	 * @return AuthorizeForOllehMarketSacRes
	 */
	public AuthorizeForOllehMarketSacRes authorizeForOllehMarketTest(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForOllehMarketSacReq req);

	/**
	 * <pre>
	 * Uplus Store 회원의 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForUplusStoreSacReq
	 * @return AuthorizeForUplusStoreSacRes
	 */
	public AuthorizeForUplusStoreSacRes authorizeForUplusStore(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForUplusStoreSacReq req);

	/**
	 * <pre>
	 * Uplus Store 회원의 회원인증 성능테스트용.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForUplusStoreSacReq
	 * @return AuthorizeForUplusStoreSacRes
	 */
	public AuthorizeForUplusStoreSacRes authorizeForUplusStoreTest(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForUplusStoreSacReq req);

	/**
	 * <pre>
	 * PayPlanet에 제공되는 3사(SKT/KT/U+) 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeV2SacReq
	 * @return AuthorizeV2SacRes
	 */
	public AuthorizeV2SacRes authorizeV2(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeV2SacReq req);

	/**
	 * <pre>
	 * PayPlanet에 제공되는 3사(SKT/KT/U+) 회원인증 V3.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeV3SacReq
	 * @return AuthorizeV3SacRes
	 */
	public AuthorizeV3SacRes authorizeV3(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeV3SacReq req);
}
