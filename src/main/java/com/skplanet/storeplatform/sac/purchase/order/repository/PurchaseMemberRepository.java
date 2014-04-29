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

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;

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
	 * 
	 * <pre>
	 * 회원의 비과금단말 / 구매차단 정책 조회.
	 * </pre>
	 * 
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @param policyCodeList
	 *            정책코드 목록
	 * @return
	 */
	public Map<String, IndividualPolicyInfoSac> getPurchaseUserPolicy(String deviceKey, List<String> policyCodeList);

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
	public SellerMbrSac searchSellerInfo(String sellerKey);
}
