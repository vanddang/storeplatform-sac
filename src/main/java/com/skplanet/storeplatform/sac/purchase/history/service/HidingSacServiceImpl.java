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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.HidingSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScResponse;

/**
 * 구매 서비스 인터페이스 구현체
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@Service
@Transactional
public class HidingSacServiceImpl implements HidingSacService {
	private static Logger logger = LoggerFactory.getLogger(HidingSacServiceImpl.class);

	@Autowired
	private HidingSCI hidingSCI;

	/**
	 * 구매내역 숨김처리.
	 * 
	 * @param hidingRequest
	 *            요청정보
	 * @return List<HidingResponse>
	 */
	@Override
	public List<HidingScResponse> updateHiding(HidingScRequest hidingRequest) {

		return this.hidingSCI.updateHiding(hidingRequest);
	}
}
