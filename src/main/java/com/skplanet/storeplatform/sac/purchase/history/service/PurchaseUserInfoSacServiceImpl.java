/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseUserInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;

/**
 * 구매내역 회원정보 변경.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, NTELS.
 */
@Service
public class PurchaseUserInfoSacServiceImpl implements PurchaseUserInfoSacService {

	@Autowired
	private PurchaseUserInfoSCI purchaseUserInfoSCI;

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return UserInfoScRes
	 */
	@Override
	public UserInfoScRes updateUserDevice(UserInfoScReq userInfoScReq) {

		return this.purchaseUserInfoSCI.updateUserDevice(userInfoScReq);
	}

}
