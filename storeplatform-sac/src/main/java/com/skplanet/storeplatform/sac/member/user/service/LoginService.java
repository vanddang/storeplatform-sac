package com.skplanet.storeplatform.sac.member.user.service;

import javax.validation.Valid;

import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import org.springframework.web.bind.annotation.RequestBody;

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
	 * prodId의 다운로드 이력여부로 tenantId를 구분한다.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	public AuthorizeForInAppSacRes authorizeForInAppV3(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req);

	/**
	 * <pre>
	 * PayPlanet에 InApp용으로 제공되는 3사 회원인증 V3.
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
	 * ID기반(Tstore ID / Social ID)회원의 인증 기능을 제공한다. [OneStore 단말을 위한 신규규격].
	 *
	 * @param requestHeader SacRequestHeader
	 * @param req           AuthorizeByIdV2SacReq
	 * @return AuthorizeByIdV2SacRes
	 */
	public AuthorizeByIdV2SacRes authorizeByIdV2(SacRequestHeader requestHeader, AuthorizeByIdV2SacReq req);

}
