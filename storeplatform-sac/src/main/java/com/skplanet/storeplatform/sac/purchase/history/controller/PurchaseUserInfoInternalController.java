/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PurchaseUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PurchaseUserInfoSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.history.service.PurchaseUserInfoSacService;

/**
 * 구매내역 회원정보 변경 컨트롤러.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
@LocalSCI
public class PurchaseUserInfoInternalController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseUserInfoSacService purchaseUserInfoSacService;

	@RequestMapping(value = "/history/userinfo/update/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseUserInfoSacRes updateUserDevice(
			@RequestBody @Validated PurchaseUserInfoSacReq purchaseUserInfoSacReq, SacRequestHeader requestHeader) {

		purchaseUserInfoSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		purchaseUserInfoSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
		this.logger.debug("PRCHS,PurchaseUserInfoInternalSCIController,SAC,REQ,{}", purchaseUserInfoSacReq);
		return this
				.resConvert(this.purchaseUserInfoSacService.updateUserDevice(this.reqConvert(purchaseUserInfoSacReq)));
	}

	/**
	 * reqConvert.
	 * 
	 * @param purchaseUserInfoSacReq
	 *            요청정보
	 * @return UserInfoScReq
	 */
	private UserInfoScReq reqConvert(PurchaseUserInfoSacReq purchaseUserInfoSacReq) {

		this.logger.debug("@@@@@@ UserInfoScReq reqConvert @@@@@@@");
		UserInfoScReq req = new UserInfoScReq();

		req.setTenantId(purchaseUserInfoSacReq.getTenantId());
		req.setSystemId(purchaseUserInfoSacReq.getSystemId());
		req.setUserKey(purchaseUserInfoSacReq.getUserKey());
		req.setNewUserKey(purchaseUserInfoSacReq.getNewUserKey());
		req.setDeviceKey(purchaseUserInfoSacReq.getDeviceKey());
		req.setNewDeviceKey(purchaseUserInfoSacReq.getNewDeviceKey());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param userInfoScRes
	 *            요청정보
	 * @return UserInfoSacRes
	 */
	private PurchaseUserInfoSacRes resConvert(UserInfoScRes userInfoScRes) {
		this.logger.debug("@@@@@@ UserInfoSacRes resConvert @@@@@@@");
		PurchaseUserInfoSacRes res = new PurchaseUserInfoSacRes();
		res.setCount(userInfoScRes.getCount());
		this.logger.info("PRCHS,PurchaseUserInfoInternalSCIController,SAC,RES,{}", res);
		return res;
	}
}
