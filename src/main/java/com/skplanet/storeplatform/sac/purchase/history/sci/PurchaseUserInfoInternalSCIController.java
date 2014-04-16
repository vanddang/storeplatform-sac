/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
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
		this.logger.debug("PRCHS,PurchaseUserInfoInternalSCIController,SAC,REQ,{}", userInfoSacInReq);
		return this.resConvert(this.purchaseUserInfoSacService.updateUserDevice(this.reqConvert(userInfoSacInReq)));
	}

	/**
	 * reqConvert.
	 * 
	 * @param userInfoSacInReq
	 *            요청정보
	 * @return UserInfoScReq
	 */
	private UserInfoScReq reqConvert(UserInfoSacInReq userInfoSacInReq) {

		this.logger.debug("@@@@@@ UserInfoScReq reqConvert @@@@@@@");
		UserInfoScReq req = new UserInfoScReq();

		if (StringUtils.isBlank(userInfoSacInReq.getTenantId())) {
			throw new StorePlatformException("SAC_PUR_0001", "TenantId");
		}
		if (StringUtils.isBlank(userInfoSacInReq.getSystemId())) {
			throw new StorePlatformException("SAC_PUR_0001", "SystemId");
		}
		if (StringUtils.isBlank(userInfoSacInReq.getUserKey())) {
			throw new StorePlatformException("SAC_PUR_0001", "UserKey");
		}
		if (StringUtils.isBlank(userInfoSacInReq.getNewUserKey())) {
			throw new StorePlatformException("SAC_PUR_0001", "NewUserKey");
		}

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
	 * @param userInfoScRes
	 *            요청정보
	 * @return UserInfoSacInRes
	 */
	private UserInfoSacInRes resConvert(UserInfoScRes userInfoScRes) {
		this.logger.debug("@@@@@@ UserInfoSacInRes resConvert @@@@@@@");
		UserInfoSacInRes res = new UserInfoSacInRes();
		res.setCount(userInfoScRes.getCount());
		this.logger.debug("PRCHS,PurchaseUserInfoInternalSCIController,SAC,RES,{}", res);
		return res;
	}
}
