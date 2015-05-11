/*
 * Copyright (c) 2015 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.service;

import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherSpecificReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Voucher Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2015. 4. 30. Updated by : 이태균, IS PLUS.
 */

public interface VoucherService {
	/**
	 * <pre>
	 * 이용권 상품 조회(자유 이용권 목록 조회).
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes FreepassListRes
	 */
	VoucherListRes searchVoucherList(VoucherListReq req, SacRequestHeader header);
	
	
	
	/**
	 * <pre>
	 * 특정 상품이 적용된 이용권 조회.
	 * </pre>
	 * 
	 * @param req
	 *            VoucherSpecificReq
	 * @param header
	 *            SacRequestHeader
	 * @return VoucherListRes VoucherListRes
	 */
	VoucherListRes searchVoucherSpecific(VoucherSpecificReq req, SacRequestHeader header);
}
