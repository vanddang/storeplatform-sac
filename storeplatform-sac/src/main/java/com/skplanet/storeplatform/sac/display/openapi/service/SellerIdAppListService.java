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

import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerIdAppListReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerIdAppListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 개발 App 목록 요청(회원 ID 기반) (CoreStoreBusiness).
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
public interface SellerIdAppListService {

	/**
	 * <pre>
	 * 개발 App 목록 요청(회원 ID 기반).
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return SellerIdAppListRes
	 */
	public SellerIdAppListRes searchSellerIdAppList(SellerIdAppListReq req, SacRequestHeader header);
}
