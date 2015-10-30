/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScReq;

/**
 * 
 * 구매SC - 구매내역 이관 서비스 구현체
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
@Service
public class PurchaseTransferSCServiceImpl implements PurchaseTransferSCService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 
	 * <pre>
	 * 구매내역 이관.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매상세 정보 리스트
	 * 
	 * @return 생성한 갯수
	 */
	@Override
	public int createPurchaseTransfer(PurchaseTransferScReq req) {
		int cnt = 0;
		try {
			cnt = (Integer) this.commonDAO.insert("PurchaseTransfer.insertPrchsTrc", req);
		} catch (DuplicateKeyException e) {
			this.logger.info("## PRCHS,ORDER,SC,CREATE,HIST,DuplicateKeyException,{}", req);
		}
		return cnt;
	}

}
