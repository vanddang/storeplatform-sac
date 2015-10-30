/**
 * 
 */
package com.skplanet.storeplatform.purchase.history.service;

import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;

/**
 * 구매내역 회원정보 변경.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, NTELS.
 */
public interface PurchaseUserInfoScService {

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return UserInfoScRes
	 */
	public UserInfoScRes updateUserDevice(UserInfoScReq userInfoScReq);
	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return String
	 */
	public String searchDeviceKey(UserInfoScReq userInfoScReq);
}
