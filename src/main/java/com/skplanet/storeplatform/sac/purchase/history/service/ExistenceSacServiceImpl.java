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
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScResponse;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
@Transactional
public class ExistenceSacServiceImpl implements ExistenceSacService {
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
	public List<ExistenceScResponse> searchExistenceList(ExistenceScRequest existenceRequest) {

		// 구매완료건만을 넣기 위한 리스트
		List<ExistenceScResponse> existenceListScResponse = new ArrayList<ExistenceScResponse>();
		// SC에서 리턴받을 리스트
		List<ExistenceScResponse> resultList = new ArrayList<ExistenceScResponse>();
		resultList = this.existenceSCI.searchExistenceList(existenceRequest);
		this.logger.debug("SAC size : {}", resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			this.logger.debug("resultList.get(i).getStatusCd() : {}", resultList.get(i).getStatusCd());
			if (resultList.get(i).getStatusCd() != null && resultList.get(i).getStatusCd().equals("OR000301")) {
				existenceListScResponse.add(resultList.get(i));
			}
		}
		return existenceListScResponse;
	}
}
