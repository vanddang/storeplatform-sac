/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AID로 상품 ID 조회 Service
 * 
 * Updated on : 2014. 3. 14. Updated by : 백승현, 인크로스.
 */
public interface OtherAIDListService {

	/**
	 * <pre>
	 * Package 정보로 상품 ID 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * 
	 * @param prodIdList
	 *            prodIdList
	 * 
	 * @return OtherAIDListRes
	 */
	public OtherAIDListRes searchProductListByAID(OtherAIDListReq req, SacRequestHeader header, List<String> aIdList);
}
