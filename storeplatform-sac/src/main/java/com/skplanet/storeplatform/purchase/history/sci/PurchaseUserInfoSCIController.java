/**
 * 
 */
package com.skplanet.storeplatform.purchase.history.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseUserInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;
import com.skplanet.storeplatform.purchase.history.service.PurchaseUserInfoScService;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, NTELS.
 */
@LocalSCI
public class PurchaseUserInfoSCIController implements PurchaseUserInfoSCI {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseUserInfoScService urchaseUserInfoScService;

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return UserInfoScRes
	 */
	@Override
	public UserInfoScRes updateUserDevice(UserInfoScReq userInfoScReq) {
		this.logger.debug("PRCHS,updateUserDevice,SC,REQ,{}", userInfoScReq);
		return this.urchaseUserInfoScService.updateUserDevice(userInfoScReq);
	}
	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return String
	 */
	@Override
	public String searchDeviceKey(UserInfoScReq userInfoScReq) {
		this.logger.debug("PRCHS,searchDeviceKey,SC,REQ,{}", userInfoScReq);
		return this.urchaseUserInfoScService.searchDeviceKey(userInfoScReq);
	}

}
