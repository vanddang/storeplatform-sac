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

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Package 정보로 상품 ID 조회 Service
 * 
 * Updated on : 2014. 3. 11. Updated by : 오승민, 인크로스.
 */
public interface OtherPackageListService {

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
	 * @return OtherPakcageListRes
	 */
	public OtherPackageListRes searchProductListByPackageNm(OtherPackageListReq req, SacRequestHeader header,
			List<String> prodIdList);
}
