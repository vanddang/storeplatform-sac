/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import com.skplanet.storeplatform.sac.purchase.order.vo.SellerMbrAppSacParam;

import java.util.List;
import java.util.Map;

/**
 * 
 * 구매 시 필요한 회원Part SAC internal I/F repository
 * 
 * Updated on : 2014. 2. 27. Updated by : 이승택, nTels.
 */
public interface PurchaseMemberRepository {

	/**
	 * 
	 * <pre>
	 * 회원/기기 키에 해당하는 회원/기기 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param userKey
	 *            내부 회원 고유 키
	 * @param deviceKey
	 *            내부 디바이스 고유 키
	 * @return 회원/기기 정보
	 */
	public PurchaseUserDevice searchUserDeviceByKey(String tenantId, String userKey, String deviceKey);

	/**
	 * 
	 * <pre>
	 * DeviceId를 이용한 등록된 회원 정보 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            디바이스 ID
	 * @param orderDt
	 *            등록정보 조회일시
	 * @return 회원 정보(userKey, deviceKey)
	 */
	public SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceId(String deviceId, String orderDt);

	/**
	 * <pre>
	 * 회원의 결제 관련 정보 조회: 통신과금 이용약관 동의여부, OCB 이용약관 동의여부, OCB 카드번호.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @return SearchUserPayplanetSacRes
	 */
	public SearchUserPayplanetSacRes searchUserPayplanet(String userKey, String deviceKey);

	/**
	 * <pre>
	 * 회원등급 조회.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * @return 회원등급
	 */
	public String searchUserGrade(String userKey);

	/**
	 * 
	 * <pre>
	 * 회원의 비과금단말 / 구매차단 정책 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param policyKey
	 *            정책 적용 Key
	 * @param policyCodeList
	 *            정책코드 목록
	 * @return
	 */
	public Map<String, IndividualPolicyInfoSac> getPurchaseUserPolicy(String tenantId, String policyKey,
			List<String> policyCodeList);

	/**
	 * 
	 * <pre>
	 * 판매자 회원 정보 조회.
	 * </pre>
	 * 
	 * @param sellerKey
	 *            판매자 내부 회원 번호
	 * @return 판매자 정보
	 */
	public SellerMbrSac detailInformation(String sellerKey, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * 2.2.2.상품상세의 판매자 정보 목록 조회.
	 * </pre>
	 * 
	 * @param sellerKey
	 *            판매자 내부 회원 번호
	 * @param tenantProdGrpCd
	 *            테넌트 상품 분류 코드
	 * @return 판매자 정보
	 */
	public SellerMbrAppSacParam detailInformationListForProduct(String sellerKey, String tenantProdGrpCd);

	/**
	 * 2.1.13.회원 한도 요금제 사용여부 업데이트.
	 *
	 * @param userKey the user key
	 * @param deviceKey the device key
	 * @param searchDt the search dt
	 * @param limitChargeYn the limit charge yn
	 */
	public void updateLimitChargeYn(String userKey, String deviceKey, String searchDt, String limitChargeYn);

	/**
	 * 2.1.15.회원 SSOCredentail정보 삭제
	 *
	 * @param userKey the user key
	 */
	public void removeSSOCredential(String userKey);

	/**
	 * 2.1.16.회원 상품권 충전 정보 등록
	 *
	 * @param userKey
	 * 		the user key
	 * @param sellerKey
	 * 		the seller key
	 * @param brandName
	 * 		the brand name
	 * @param chargerId
	 * 		the brand site id
	 * @param chargerName
	 * 		the charger name
	 */
	void createGiftChargeInfo(String userKey, String sellerKey, String brandName, String brandId,  String chargerId,
			String chargerName);
}
