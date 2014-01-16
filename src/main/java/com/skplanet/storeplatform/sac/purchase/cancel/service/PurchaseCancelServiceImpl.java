/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelResult;

/**
 * 구매 취소 Service Implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Service
@Transactional
public class PurchaseCancelServiceImpl implements PurchaseCancelService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseCancelServiceImpl.class);

	@Override
	public PurchaseCancelResult cancelPurchase(PurchaseCancelParam purchaseCancelParam) {

		// TODO : 구매 취소 프로세스
		// LOOP 시작
		// TR 시작
		// DB 업데이트
		//
		// 결제 취소
		// TR 끝
		// 결과 카운트 및 리턴

		return null;

	}

}
