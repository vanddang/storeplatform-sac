/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * DeviceProfileService Service 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, 인크로스
 */
@Service
public class SellerAppListServiceImpl implements SellerAppListService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.product.service.DeviceProfileService#searchDeviceProfile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SellerAppListRes searchSellerAppList(SellerAppListReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		CommonResponse commonResponse = new CommonResponse();
		SellerAppListRes res = new SellerAppListRes();
		// DeviceProfile deviceProfile = this.commonDAO.queryForObject("DeviceProfile.selectDeviceProfile", requestVO,
		// DeviceProfile.class);

		return res;
	}
}
