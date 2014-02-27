/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseUserInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInRes;
import com.skplanet.storeplatform.sac.purchase.history.service.PurchaseUserInfoSacService;

/**
 * 구매내역 회원정보 변경 컨트롤러.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class PurchaseUserInfoInternalSCIController implements PurchaseUserInfoInternalSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseUserInfoSacService purchaseUserInfoSacService;

	@Override
	public UserInfoSacInRes updateUserDevice(UserInfoSacInReq userInfoSacInReq) {
		return this.resConvert(this.purchaseUserInfoSacService.updateUserDevice(this.reqConvert(userInfoSacInReq)));
	}

	/**
	 * reqConvert.
	 * 
	 * @param autoPaymentCancelSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return AutoPaymentCancelScReq
	 */
	private UserInfoScReq reqConvert(UserInfoSacInReq userInfoSacInReq) {

		this.logger.debug("@@@@@@ AutoPaymentCancelSac reqConvert @@@@@@@");
		UserInfoScReq req = new UserInfoScReq();

		req.setTenantId(userInfoSacInReq.getTenantId());
		req.setSystemId(userInfoSacInReq.getSystemId());
		req.setUserKey(userInfoSacInReq.getUserKey());
		req.setNewUserKey(userInfoSacInReq.getNewUserKey());
		req.setDeviceKey(userInfoSacInReq.getDeviceKey());
		req.setNewDeviceKey(userInfoSacInReq.getNewDeviceKey());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param autoPaymentCancelScRes
	 *            요청정보
	 * @return UserInfoSacInRes
	 */
	private UserInfoSacInRes resConvert(UserInfoScRes userInfoScRes) {
		this.logger.debug("@@@@@@ AutoPaymentCancelSac resConvert @@@@@@@");
		UserInfoSacInRes res = new UserInfoSacInRes();
		res.setCount(userInfoScRes.getCount());
		return res;
	}
}
