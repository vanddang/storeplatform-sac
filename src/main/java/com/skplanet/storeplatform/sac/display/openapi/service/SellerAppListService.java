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

import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 상품 ID에 대한 단말 Provisioning 조회 Service
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public interface SellerAppListService {
	/**
	 * <pre>
	 * 상품 ID에 대한 단말 Provisioning 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	public SellerAppListRes searchSellerAppList(SellerAppListReq req, SacRequestHeader header);
}
