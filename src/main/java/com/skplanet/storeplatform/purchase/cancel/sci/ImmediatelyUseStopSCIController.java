/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.sci;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.cancel.service.ImmediatelyUseStopScService;
import com.skplanet.storeplatform.purchase.client.cancel.sci.ImmediatelyUseStopSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;

/**
 * 구매내역 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class ImmediatelyUseStopSCIController implements ImmediatelyUseStopSCI {

	@Autowired
	private ImmediatelyUseStopScService service;

	/**
	 * 구매이력을 조회한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return PrchsDtl
	 */
	@Override
	public List<PrchsDtl> searchPrchsDtl(ImmediatelyUseStopScReq request) {
		return this.service.searchPrchsDtl(request);
	}

	/**
	 * 즉시이용정지 기능을 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return ImmediatelyUseStopScRes
	 */
	@Override
	public ImmediatelyUseStopScRes updateUseStop(ImmediatelyUseStopScReq request) {
		return this.service.updateUseStop(request);
	}

}
