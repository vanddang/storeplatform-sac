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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseTransferSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferSc;
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

		PurchaseTransferScReq purchaseTransferScReq = new PurchaseTransferScReq();

		List<PurchaseTransferSc> purchaseTransferScList = new ArrayList<PurchaseTransferSc>();
		PurchaseTransferSc purchaseTransferSc;

		purchaseTransferScReq.setTenantId(request.getTenantId());
		purchaseTransferScReq.setMarketDeviceKey(request.getMarketDeviceKey());
		purchaseTransferScReq.setPrchsCount(request.getPrchsCount());
		purchaseTransferScReq.setSystemId(request.getSystemId());

		for (PurchaseTransferSac purchaseTransferSac : request.getPrchsList()) {
			purchaseTransferSc = new PurchaseTransferSc();
			purchaseTransferSc.setPrchsDt(purchaseTransferSac.getPrchsDt());
			purchaseTransferSc.setMarketPrchsId(purchaseTransferSac.getMarketPrchsId());
			purchaseTransferSc.setMarketProdId(purchaseTransferSac.getMarketProdId());

			purchaseTransferScList.add(purchaseTransferSc);
		}
		purchaseTransferScReq.setPrchsList(purchaseTransferScList);

		this.purchaseTransferSCI.createPurchaseTransfer(purchaseTransferScReq);

		PurchaseTransferSacRes response = new PurchaseTransferSacRes();
		response.setCode(PurchaseConstants.SAP_SUCCESS);
		response.setMessage("");
		return response;
	}
}
