/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 즉시이용정지 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class ImmediatelyUseStopScServiceImpl implements ImmediatelyUseStopScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 구매이력을 조회한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return PrchsDtl
	 */
	@Override
	public List<PrchsDtl> searchPrchsDtl(ImmediatelyUseStopScReq request) {
		return this.commonDAO.queryForList("UseStop.searchPrchsDtl", request, PrchsDtl.class);
	}

	/**
	 * 즉시이용정지 UPDATE 기능 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지
	 * @return HistoryListScRes
	 */
	@Override
	public ImmediatelyUseStopScRes updateUseStop(ImmediatelyUseStopScReq request) {

		this.logger.debug("ImmediatelyUseStop Sc Request Param : {}" + request);

		request.setPrchsStatusCd(PurchaseCDConstants.PRCHS_STATUS_DRBK); // 구매상태(환불:OR000305)
		request.setPaymentMtdCd(PurchaseCDConstants.PAYMENT_METHOD_FIXRATE_REFUND); // 결제수단코드(정액권 환불:OR000692)
		request.setStatusCd(PurchaseCDConstants.PRCHS_STATUS_COMPT); // 구매상태(완료:OR000305)

		this.commonDAO.update("UseStop.updatePrchsUseFix", request); // 구매 - 정액권으로 이용한 상품 update
		this.commonDAO.update("UseStop.updatePrchsDtlUseFix", request); // 구매상세 - 정액권으로 이용한 상품 update

		this.commonDAO.update("UseStop.updatePrchs", request); // 구매Update
		this.commonDAO.update("UseStop.updatePrchsDtl", request);// 구매상세Update
		this.commonDAO.update("UseStop.updateAutoPrchs", request);// 자동구매Update
		this.commonDAO.insert("UseStop.insertPayment", request);// 결제Insert

		ImmediatelyUseStopScRes response = new ImmediatelyUseStopScRes();
		response.setPrchsId(request.getPrchsId());

		return response;
	}
}
