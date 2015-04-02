/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseTransferSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSacRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * 구매내역 이관 구현
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
@Service
public class PurchaseTransferServiceImpl implements PurchaseTransferService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseTransferSCI purchaseTransferSCI;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 
	 * <pre>
	 * 구매내역 이관 처리.
	 * </pre>
	 * 
	 * @param request
	 *            구매이관요청 정보
	 * @return CreatePurchaseSacRes
	 */
	@Override
	public PurchaseTransferSacRes createPurchaseTransfer(PurchaseTransferSacReq request) {

		PurchaseTransferScReq purchaseTransferScReq;

		int count = 0;
		int succCount = 0;
		int failCount = 0;

		for (PurchaseTransferSac purchaseTransferSac : request.getPrchsList()) {

			purchaseTransferScReq = new PurchaseTransferScReq();
			purchaseTransferScReq.setTenantId(request.getTenantId());
			purchaseTransferScReq.setMarketDeviceKey(request.getMarketDeviceKey());
			purchaseTransferScReq.setPrchsCount(request.getPrchsCount());
			purchaseTransferScReq.setSystemId(request.getSystemId());

			purchaseTransferScReq.setPrchsDt(purchaseTransferSac.getPrchsDt());
			purchaseTransferScReq.setMarketPrchsId(purchaseTransferSac.getMarketPrchsId());
			purchaseTransferScReq.setMarketProdId(purchaseTransferSac.getMarketProdId());
			purchaseTransferScReq.setStatusCd(purchaseTransferSac.getStatusCd());
			purchaseTransferScReq.setCancelDt(purchaseTransferSac.getCancelDt());
			purchaseTransferScReq.setTrcResultCd(PurchaseConstants.TRC_RESULT_PLAN);

			try {
				this.purchaseTransferSCI.createPurchaseTransfer(purchaseTransferScReq);
				succCount++;
			} catch (Exception e) {
				this.logger.info("## PRCHS,ORDER,SAC,CREATE,HIST,FAIL,{},{},{}",
						purchaseTransferScReq.getMarketPrchsId(), purchaseTransferScReq.getMarketDeviceKey(), e);
				failCount++;
			}
			count++;
		}

		PurchaseTransferSacRes response = new PurchaseTransferSacRes();

		this.logger.info("## PRCHS,ORDER,SAC,CREATE,HIST,TOTAL,CNT {}", count);
		this.logger.info("## PRCHS,ORDER,SAC,CREATE,HIST,SUCC,CNT {}", succCount);
		this.logger.info("## PRCHS,ORDER,SAC,CREATE,HIST,FAIL,CNT {}", failCount);

		if (succCount > 0) {
			response.setCode(PurchaseConstants.SAP_SUCCESS);
			response.setMessage("SUCCESS COUNT : " + succCount);
		} else {
			response.setCode("SAC_PUR_7999");
			response.setMessage(this.messageSourceAccessor.getMessage("SAC_PUR_7999"));
		}

		return response;
	}

}
