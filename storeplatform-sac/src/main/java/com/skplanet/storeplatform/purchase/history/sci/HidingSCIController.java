/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.sci;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.HidingSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;
import com.skplanet.storeplatform.purchase.history.service.HidingScService;

/**
 * 구매 컴포넌트 인터페이스 컨트롤러
 * 
 * Updated on : 2013-12-20 Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class HidingSCIController implements HidingSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HidingScService hidingScService;

	/**
	 * 구매내역 숨김처리.
	 * 
	 * @param hidingScReq
	 *            요청정보
	 * @return List<HidingScRes>
	 */
	@Override
	public List<HidingScRes> updateHiding(HidingScReq hidingScReq) {
		this.logger.debug("PRCHS,HidingSCIController,SC,REQ,{}", hidingScReq);
		return this.hidingScService.updateHiding(hidingScReq);
	}
}
