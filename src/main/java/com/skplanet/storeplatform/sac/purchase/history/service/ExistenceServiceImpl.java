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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceResponse;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
@Transactional
public class ExistenceServiceImpl implements ExistenceService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSCI existenceSCI;

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceRequest
	 *            요청정보
	 * @return List<ExistenceResponse>
	 */
	@Override
	public List<ExistenceResponse> listExist(ExistenceRequest existenceRequest) {
		List<ExistenceResponse> existenceListResponse = new ArrayList<ExistenceResponse>();
		List<ExistenceResponse> resultList = new ArrayList<ExistenceResponse>();
		resultList = this.existenceSCI.listExist(existenceRequest);
		this.logger.debug("SAC size : {}", resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			this.logger.debug("resultList.get(i).getStatusCd() : {}", resultList.get(i).getStatusCd());
			if (resultList.get(i).getStatusCd() != null && resultList.get(i).getStatusCd().equals("OR000301")) {
				existenceListResponse.add(resultList.get(i));
			}
		}

		return existenceListResponse;
	}
}
