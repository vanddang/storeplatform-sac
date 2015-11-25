/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.cancel.sci;

import java.util.List;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;

/**
 * Class 즉시이용정지 기능을 제공하는 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface ImmediatelyUseStopSCI {

	/**
	 * 구매이력을 조회한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return PrchsDtl
	 */
	public List<PrchsDtl> searchPrchsDtl(ImmediatelyUseStopScReq request);

	/**
	 * 즉시이용정지 기능을 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return ImmediatelyUseStopScRes
	 */
	public ImmediatelyUseStopScRes updateUseStop(ImmediatelyUseStopScReq request);

}
