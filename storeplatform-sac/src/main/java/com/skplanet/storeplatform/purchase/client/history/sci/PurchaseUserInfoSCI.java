package com.skplanet.storeplatform.purchase.client.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;

/**
 * 구매내역 회원정보 변경 컨트롤러.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, 엔텔스.
 */
@SCI
public interface PurchaseUserInfoSCI {

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
