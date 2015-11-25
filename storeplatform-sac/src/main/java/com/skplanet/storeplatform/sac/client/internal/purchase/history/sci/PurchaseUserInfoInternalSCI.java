/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInRes;

/**
 * 구매내역 회원정보 변경 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface PurchaseUserInfoInternalSCI {

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoSacInReq
	 *            구매내역요청
	 * @return UserInfoSacInRes
	 */
	public UserInfoSacInRes updateUserDevice(UserInfoSacInReq userInfoSacInReq);

}
