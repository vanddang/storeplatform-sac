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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.SearchDcdSupportProductSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.DcdSupportProductRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseUserInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;

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

	/**
	 * <pre>
	 * 회원 OGG 관련 구매/기타 내부메서드 호출.
	 * </pre>
	 * 
	 * @param isCall
	 *            내부메서드 연동여부
	 * @param systemId
	 *            Sysetm ID
	 * @param tenantId
	 *            Tenant ID
	 * @param userKey
	 *            사용자 Key
	 * @param previousUserKey
	 *            이전 사용자 Key
	 * @param deviceKey
	 *            휴대기기 Key
	 * @param previousDeviceKey
	 *            이전 휴대기기 Key
	 */
	public void excuteInternalMethod(boolean isCall, String systemId, String tenantId, String userKey, String previousUserKey, String deviceKey,
			String previousDeviceKey) {

		//StackTraceElement[] ste = new Throwable().getStackTrace();
		//String methodName = ste[1].getMethodName();
		//				regDeviceInfo : 회원전환(모바일회원 -> ID회원으로 전환);
		//				rXCreateUserIDP : 통합회원 전환생성정보를 사이트에 배포 - CMD : RXCreateUserIDP
		//				rXUpdateAgreeUserIDP : 이용동의 변경사이트 목록 배포 - CMD : rXUpdateAgreeUserIDP
		//				authorizeSaveAndSyncByMac : Save&Sync 인증

		//LOGGER.info("회원 OGG 관련 구매/기타 내부메서드 호출 메서드: {}", ste[1].getMethodName());

		if (isCall) {

			/* 1. 기타/전시 파트 userKey 변경 */
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
			LOGGER.info("tstoreTransferSCI.transferOwner response : {}", this.tstoreTransferSCI.transferOwner(tStoreTransferOwnerEcReq));

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
}
