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

import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class ExistenceSacServiceImpl implements ExistenceSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSCI existenceSCI;

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceReq
	 *            요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public List<ExistenceScRes> searchExistenceList(ExistenceScReq existenceReq) {

		// 기구매내역 조회함
		List<ExistenceScRes> resultList = this.existenceSCI.searchExistenceList(existenceReq);
		// 구매상태가 구매완료건만을 넣기 위한 리스트
		List<ExistenceScRes> existenceListScRes = new ArrayList<ExistenceScRes>();
		// 구매완료상태만 add 한다.
		for (ExistenceScRes existenceScRes : resultList) {
			this.logger.debug("existenceScRes.getStatusCd() : {}", existenceScRes.getStatusCd());
			if (existenceScRes.getStatusCd() != null
					&& existenceScRes.getStatusCd().equals(PurchaseConstants.PRCHS_STATUS_COMPT)) {
				existenceListScRes.add(existenceScRes);
			}
		}
		return existenceListScRes;
	}
}
