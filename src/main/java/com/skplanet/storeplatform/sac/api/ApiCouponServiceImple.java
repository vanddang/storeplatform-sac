/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.action.CallShoppin;
import com.skplanet.storeplatform.sac.api.vo.CouponParameterInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;

@Component
public class ApiCouponServiceImple implements ApiCouponService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public CouponResponseInfo doLoadBalance(CouponParameterInfo couponParameterInfo) {
		CouponResponseInfo responseVO = null;

		// this.log.info("<CouponControl> dePloy...txID = " + couponParameterInfo.getTxId());
		// this.log.info("<CouponControl> dePloy...txTYPE = " + couponParameterInfo.getTxType());
		// this.log.info("<CouponControl> dePloy...XML = " + couponParameterInfo.getRData());
		try {
			CallShoppin call = new CallShoppin();
			call.startApi(couponParameterInfo);
		} catch (Exception e) {

		}
		return responseVO;

	}
}
