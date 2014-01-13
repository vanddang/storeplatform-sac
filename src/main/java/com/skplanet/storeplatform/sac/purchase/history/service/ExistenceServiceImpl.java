/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceResponse;

/**
 * Member Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013-09-01 Updated by : $$Id$$.
 */
@Service
public class ExistenceServiceImpl implements ExistenceService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSCI existenceSCI;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param GiftReceiveRequest
	 *            선물수신확인 체크
	 * @return GiftReceiveResponse
	 */
	@Override
	public List<ExistenceResponse> getExist(ExistenceRequest existenceRequest) {

		return this.existenceSCI.getExist(existenceRequest);
	}

}
