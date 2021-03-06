/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.tstore.sci.TstoreTransferSCI;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreTransferOwnerEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreTransferOwnerEcRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.IapProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.SearchDcdSupportProductSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UserDownloadInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.DcdSupportProductRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseUserInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;

/**
 * 내부 메서드 호출 기능을 공통 정의해서 사용한다.
 * 
 * Updated on : 2014. 5. 19. Updated by : 반범진, 지티소프트.
 */
@Component
public class MemberCommonInternalComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCommonInternalComponent.class);

	@Autowired
	private ChangeDisplayUserSCI changeDisplayUserSCI;

	@Autowired
	private PurchaseUserInfoInternalSCI purchaseUserInfoInternalSCI;

	@Autowired
	private SearchDcdSupportProductSCI searchDcdSupportProductSCI;

	@Autowired
	private ExistenceInternalSacSCI existenceInternalSacSCI;

	@Autowired
	private TstoreTransferSCI tstoreTransferSCI;

	@Autowired
	IapProductInfoSCI iapProductInfoSCI;

	@Autowired
	UserDownloadInfoSCI userDownloadInfoSCI;

	/**
	 * <pre>
	 * 회원 OGG 관련 구매/기타 내부메서드 호출.
	 * </pre>
	 * 
	 * @param isCall
	 *            내부메서드 연동여부
	 * @param systemId
	 *            Sysetm ID
	 * @param userKey
	 *            사용자 Key
	 * @param previousUserKey
	 *            이전 사용자 Key
	 * @param deviceKey
	 *            휴대기기 Key
	 * @param previousDeviceKey
	 *            이전 휴대기기 Key
	 */
	public void excuteInternalMethod(boolean isCall, String systemId, String userKey,
			String previousUserKey, String deviceKey, String previousDeviceKey) {

		// TODO. 타파트 개발 완료전 오류방지를 위해 임의로 SO1 셋팅
		String tenantId = "S01";
		if (isCall) {

			// 1. 기타/전시 파트 userKey 변경
			ChangeDisplayUserSacReq changeDisplayUserSacReq = new ChangeDisplayUserSacReq();
			changeDisplayUserSacReq.setNewUseKey(userKey);
			changeDisplayUserSacReq.setOldUserKey(previousUserKey);
			changeDisplayUserSacReq.setTenantId(tenantId);
			this.changeDisplayUserSCI.changeUserKey(changeDisplayUserSacReq);

			/* 2. 구매 파트 userKey, deviceKey 변경 */
			UserInfoSacInReq userInfoSacInReq = new UserInfoSacInReq();
			userInfoSacInReq.setSystemId(systemId);
			userInfoSacInReq.setTenantId(tenantId);
			userInfoSacInReq.setNewDeviceKey(deviceKey);
			userInfoSacInReq.setDeviceKey(previousDeviceKey);
			userInfoSacInReq.setUserKey(previousUserKey);
			userInfoSacInReq.setNewUserKey(userKey);
			this.purchaseUserInfoInternalSCI.updateUserDevice(userInfoSacInReq);

			/* 3. tenant Cash, 쿠폰 이관요청 */
			TStoreTransferOwnerEcReq tStoreTransferOwnerEcReq = new TStoreTransferOwnerEcReq();
			tStoreTransferOwnerEcReq.setUserKey(userKey);
			tStoreTransferOwnerEcReq.setOldUserKey(previousUserKey);
			LOGGER.info("tstoreTransferSCI.transferOwner request : {}, {}", tStoreTransferOwnerEcReq.getUserKey(),
					tStoreTransferOwnerEcReq.getOldUserKey());
			TStoreTransferOwnerEcRes tStoreTransferOwnerEcRes = this.tstoreTransferSCI
					.transferOwner(tStoreTransferOwnerEcReq);
			LOGGER.info("tstoreTransferSCI.transferOwner response resultCd : {}, resultMsg : {}",
					tStoreTransferOwnerEcRes.getResultCd(), tStoreTransferOwnerEcRes.getResultMsg());

		}

	}

	/**
	 * <pre>
	 * 기타/전시 파트 아이디 변경 요청.
	 * </pre>
	 * 
	 * @param req
	 *            ChangeDisplayUserSacReq
	 */
	public void changeUserId(ChangeDisplayUserSacReq req) {
		LOGGER.info("changeDisplayUserSCI.changeUserId request : {}", req);
		this.changeDisplayUserSCI.changeUserId(req);
	}

	/**
	 * <pre>
	 * 기타/전시 파트 Dcd 상품 조회.
	 * </pre>
	 * 
	 * @return ExistenceListRes
	 */
	public DcdSupportProductRes srhDcdSupportProduct() {
		return this.searchDcdSupportProductSCI.searchDcdSupportProduct();
	}

	/**
	 * <pre>
	 * 기타/전시 파트 기구매내역 체크.
	 * </pre>
	 * 
	 * @param req
	 *            ExistenceReq
	 * @return ExistenceListRes
	 */
	public ExistenceListRes srhExistenceList(ExistenceReq req) {
		return this.existenceInternalSacSCI.searchExistenceList(req);
	}

	/**
	 * <pre>
	 * IAP상품 정보조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            String
	 * @param deviceId
	 *            String
	 * @param prodId
	 *            String
	 * @return IapProductInfoRes
	 */
	public IapProductInfoRes getIapProdInfo(String tenantId, String deviceId, String prodId) {
		IapProductInfoReq req = new IapProductInfoReq();
		req.setTenantId(tenantId);
		req.setPartProdId(prodId);

		LOGGER.info("{} getIapProductInfo Request : {}", deviceId, ConvertMapperUtils.convertObjectToJson(req));
		IapProductInfoRes res = this.iapProductInfoSCI.getIapProductInfo(req);
		boolean exists = res != null;
		if (exists) {
			LOGGER.info("{} getIapProductInfo Response : {}", deviceId, ConvertMapperUtils.convertObjectToJson(res));
		} else {
			LOGGER.info("{} getIapProductInfo Response : {}", deviceId, res);
		}

		return res;
	}

	/**
	 * <pre>
	 * 사용자의 최근 다운로드 정보를 조회한다. 조회 대상이 없는 경우 null을 응답한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            String
	 * @param deviceId
	 *            String
	 * @param prodId
	 *            String
	 * @return UserDownloadInfoRes
	 */
	public UserDownloadInfoRes getUserDownloadInfo(String tenantId, String deviceId, String prodId) {

		UserDownloadInfoReq req = new UserDownloadInfoReq();
		req.setTenantId(tenantId);
		req.setMdn(deviceId);
		req.setPartProdId(prodId);

		LOGGER.info("{} getUserDownloadInfo Request : {}", deviceId, ConvertMapperUtils.convertObjectToJson(req));
		UserDownloadInfoRes res = this.userDownloadInfoSCI.getUserDownloadInfo(req);
		boolean exists = res != null;
		if (exists) {
			LOGGER.info("{} getUserDownloadInfo Response : {}", deviceId,
					ConvertMapperUtils.defaultConvertObjectToJson(res));
		} else {
			LOGGER.info("{} getUserDownloadInfo Response : {}", deviceId, res);
		}

		return res;

	}
}
