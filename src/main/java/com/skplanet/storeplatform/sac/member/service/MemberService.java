/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.service;

import com.skplanet.storeplatform.sac.client.user.vo.MyPagePurchase;
import com.skplanet.storeplatform.sc.client.vo.UserCareerSearch;

/**
 * Member Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013-09-01 Updated by : 아키텍쳐팀, SK플래닛.
 */
public interface MemberService {

	/**
	 * MyPage 구매/결제 이력 상세 조회.
	 * 
	 * @param myPagePurchaseVO
	 *            마이페이지 구매/결제 내역
	 * @return 마이페이지 구매/결제 내역
	 */
	MyPagePurchase searchMypagePurchase(MyPagePurchase myPagePurchase);

	UserCareerSearch searchUserCareerSearchVO(String id, String userId, String careerId);

	void createUser(UserCareerSearch userCareerSearchVO);

	void searchUserCareerSearchVOGetParam(String id, String userId, String careerId);

	void searchUserCareerSearchVOGetObject(UserCareerSearch uerCareerSearchVO);
}
